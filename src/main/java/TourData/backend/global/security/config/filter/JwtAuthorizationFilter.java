package TourData.backend.global.security.config.filter;

import static TourData.backend.global.security.jwt.JwtProperties.COOKIE_NAME;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import TourData.backend.global.security.auth.CustomUserDetailsService;
import TourData.backend.global.security.jwt.TokenProvider;
import TourData.backend.global.security.utils.ResponseWriter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

// 인가 필터
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final TokenProvider tokenProvider;
    private final ResponseWriter responseWriter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 쿠키에서 토큰 추출
        String token = getTokenFromCookie(request);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        // 토큰 검증 및 인가
        try {
            tokenProvider.validateToken(token);
            // 인증 정보 추출
            Authentication authentication = customUserDetailsService.extractAuthentication(token);
            // 사용자 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 예외 처리
        catch (Exception e) {
            responseWriter.setErrorResponse(response, SC_UNAUTHORIZED, e.getMessage());
        }
        chain.doFilter(request, response);
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        return Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> COOKIE_NAME.getValue().equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

}