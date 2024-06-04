package TourData.backend.global.security.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import TourData.backend.global.security.config.filter.JwtAuthenticationFilter;
import TourData.backend.global.security.config.filter.JwtAuthorizationFilter;
import TourData.backend.global.security.auth.CustomUserDetailsService;
import TourData.backend.global.security.config.handler.JwtAccessDeniedHandler;
import TourData.backend.global.security.config.handler.JwtAuthenticationEntryPoint;
import TourData.backend.global.security.config.handler.JwtAuthenticationFailureHandler;
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
                .exceptionHandling()
                .accessDeniedHandler(new JwtAccessDeniedHandler(responseWriter))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint(responseWriter))
                .and()
                .authorizeHttpRequests((authz) -> authz
                        // 회원 가입
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/users")).permitAll()
                        .anyRequest().authenticated()
                );
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
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            // 인증 필터 설정
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, customUserDetailsService);
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler(responseWriter));
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

            // 인가 필터 설정
            JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(customUserDetailsService, responseWriter);

            http
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

}