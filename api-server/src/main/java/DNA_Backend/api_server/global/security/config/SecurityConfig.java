package DNA_Backend.api_server.global.security.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import DNA_Backend.api_server.global.security.config.filter.JwtAuthenticationFilter;
import DNA_Backend.api_server.global.security.config.filter.JwtAuthorizationFilter;
import DNA_Backend.api_server.global.security.config.handler.JwtAccessDeniedHandler;
import DNA_Backend.api_server.global.security.config.handler.JwtAuthenticationEntryPoint;
import DNA_Backend.api_server.global.security.config.handler.JwtAuthenticationFailureHandler;
import DNA_Backend.api_server.global.security.config.handler.OAuth2LoginSuccessHandler;
import DNA_Backend.api_server.global.security.oauth.Oauth2UserServiceCustom;
import DNA_Backend.api_server.global.security.cookie.CookieManager;
import DNA_Backend.api_server.global.security.jwt.TokenManager;
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

    private final Oauth2UserServiceCustom oauth2UserServiceCustom;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    private final TokenManager tokenManager;
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
                        // 이름 중복 여부 확인
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/users/name/check")).permitAll()
                        // 이메일 인증 코드 전송, 검증
                        .requestMatchers(HttpMethod.POST, "api/users/email/**").permitAll()
                        // 전체 지역 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations")).permitAll()
                        // 단일 지역 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations/{\\d+}")).permitAll()
                        // 전체 지역 날씨 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations/weather")).permitAll()
                        // 단일 지역 날씨 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations/{\\d+}/weather")).permitAll()
                        // 단일 지역 좋아요 수 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations/{\\d+}/like/count")).permitAll()
                        // 시설 검색 by 지역 id & 타입
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations/{\\d+}/facilities/search")).permitAll()
                        // 전체 지역 총 시설 수 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations/facilities/count")).permitAll()
                        // 단일 지역 총 시설 수 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations/{\\d+}/facilities/count")).permitAll()
                        // 단일 지역 워케이션 리뷰 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/locations/{\\d+}/reviews")).permitAll()
                        // 시설 검색 by 위도, 경도 & 타입
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/facilities/search")).permitAll()
                        // 전체 워케이션 리뷰 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/reviews")).permitAll()
                        // Swagger
                        .requestMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                // OAuth2 인증
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oAuth2LoginSuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oauth2UserServiceCustom)
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
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager,
                    tokenManager, cookieManager);
            jwtAuthenticationFilter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");

            http
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

}