package DNA_Backend.api_server.global.security.config.filter;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import DNA_Backend.api_server.global.security.auth.CustomUserDetailsService;
import DNA_Backend.api_server.global.security.utils.CookieManager;
import DNA_Backend.api_server.global.security.utils.ResponseWriter;
import DNA_Backend.api_server.global.security.utils.TokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

// 인가 필터
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final TokenManager tokenManager;
    private final ResponseWriter responseWriter;
    private final CookieManager cookieManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 쿠키에서 토큰 추출
        String token = cookieManager.getTokenFromCookie(request);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        // 토큰 검증 및 인가
        try {
            tokenManager.validateToken(token);
            // 인증 정보 추출
            Authentication authentication = customUserDetailsService.extractAuthentication(token);
            // 사용자 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 예외 처리
        catch (Exception e) {
            responseWriter.setErrorResponse(response, SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        chain.doFilter(request, response);
    }

}