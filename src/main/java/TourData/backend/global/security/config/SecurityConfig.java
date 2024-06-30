package TourData.backend.global.security.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import TourData.backend.global.security.config.filter.JwtAuthenticationFilter;
import TourData.backend.global.security.config.filter.JwtAuthorizationFilter;
import TourData.backend.global.security.auth.CustomUserDetailsService;
import TourData.backend.global.security.config.handler.JwtAccessDeniedHandler;
import TourData.backend.global.security.config.handler.JwtAuthenticationEntryPoint;
import TourData.backend.global.security.config.handler.JwtAuthenticationFailureHandler;
import TourData.backend.global.security.config.handler.OAuth2LoginSuccessHandler;
import TourData.backend.global.security.jwt.TokenProvider;
import TourData.backend.global.security.oauth.CustomOauth2UserService;
import TourData.backend.global.security.utils.ResponseWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOauth2UserService customOauth2UserService;
    private final TokenProvider tokenProvider;
    private final ResponseWriter responseWriter;

    // 보안 필터 체인 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // JWT 인증
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                // 커스텀 필터 적용
                .apply(new MyCustomFilter())
                .and()
                // 예외 처리 핸들러
                .exceptionHandling()
                .accessDeniedHandler(new JwtAccessDeniedHandler(responseWriter))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint(responseWriter))
                .and()
                .authorizeHttpRequests((authz) -> authz
                        // 회원 가입
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/users")).permitAll()
                        // 지역 전체 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations")).permitAll()
                        // 지역 좋아요 수 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations/{\\d+}/like/count")).permitAll()
                        // Swagger
                        .requestMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Oauth2 인증
                .oauth2Login()
                .successHandler(new OAuth2LoginSuccessHandler(tokenProvider, responseWriter))
                .userInfoEndpoint()
                .userService(customOauth2UserService);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 비밀번호 인코더
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 커스텀 필터 설정
    public class MyCustomFilter extends AbstractHttpConfigurer<MyCustomFilter, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            // 인증 필터 설정
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, tokenProvider, responseWriter);
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler(responseWriter));
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");

            // 인가 필터 설정
            JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(customUserDetailsService, tokenProvider, responseWriter);

            http
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

}