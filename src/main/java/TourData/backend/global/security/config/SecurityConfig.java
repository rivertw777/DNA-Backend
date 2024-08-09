package TourData.backend.global.security.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import TourData.backend.global.security.config.filter.JwtAuthenticationFilter;
import TourData.backend.global.security.config.filter.JwtAuthorizationFilter;
import TourData.backend.global.security.config.handler.JwtAccessDeniedHandler;
import TourData.backend.global.security.config.handler.JwtAuthenticationEntryPoint;
import TourData.backend.global.security.config.handler.JwtAuthenticationFailureHandler;
import TourData.backend.global.security.config.handler.OAuth2LoginSuccessHandler;
import TourData.backend.global.security.jwt.TokenProvider;
import TourData.backend.global.security.oauth.CustomOauth2UserService;
import TourData.backend.global.security.utils.CookieManager;
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

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final CustomOauth2UserService customOauth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    private final TokenProvider tokenProvider;
    private final CookieManager cookieManager;

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
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .authorizeHttpRequests(authz -> authz
                        // 회원 가입
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/users")).permitAll()
                        // 이름 중복 체크
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/users/names/validate")).permitAll()
                        // 이메일 인증 코드 전송, 검증
                        .requestMatchers(HttpMethod.POST, "api/users/emails/**").permitAll()
                        // 지역 전체 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations")).permitAll()
                        // 전체 지역 날씨 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations/weather")).permitAll()
                        // 지역 좋아요 수 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations/{\\d+}/like/count")).permitAll()
                        // 시설 검색
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/facilities/search/**")).permitAll()
                        // 지역 내 시설 수 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/facilities/count")).permitAll()
                        // 웹소켓 연결
                        .requestMatchers("/ws/**").permitAll()
                        // Swagger
                        .requestMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                // OAuth2 인증
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oAuth2LoginSuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOauth2UserService)
                        )
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

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
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, tokenProvider, cookieManager);
            jwtAuthenticationFilter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");

            http
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

}