package TourData.backend.global.security.jwt;

import static TourData.backend.global.security.jwt.JwtProperties.HEADER_STRING;
import static TourData.backend.global.security.jwt.JwtProperties.TOKEN_PREFIX;

import TourData.backend.global.security.auth.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

// 인가 필터
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 헤더에 토큰 정보 확인
        String header = request.getHeader(HEADER_STRING.getValue());
        if (header == null || !header.startsWith(TOKEN_PREFIX.getValue())) {
            chain.doFilter(request, response);
            return;
        }
        // 헤더에서 토큰 정보 추출
        String token = request.getHeader(HEADER_STRING.getValue()).replace(TOKEN_PREFIX.getValue(), "");
        // 토큰 검증 및 인가
        try {
            customUserDetailsService.validateToken(token);
            // 인증 정보 추출
            Authentication authentication = customUserDetailsService.extractAuthentication(token);
            // 사용자 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 나머지 예외 상황
        catch (Exception e) {
        }
        chain.doFilter(request, response);
    }

}