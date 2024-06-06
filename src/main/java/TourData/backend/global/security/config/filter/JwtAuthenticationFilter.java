package TourData.backend.global.security.config.filter;

import TourData.backend.global.security.dto.TokenResponse;
import TourData.backend.global.security.dto.UserLoginRequest;
import TourData.backend.global.security.auth.CustomUserDetails;
import TourData.backend.global.security.auth.CustomUserDetailsService;
import TourData.backend.global.security.utils.ResponseWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 인증 필터
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final ResponseWriter responseWriter;

    // 인증 시도
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 요청에서 로그인 정보 가져오기
            ObjectMapper om = new ObjectMapper();
            UserLoginRequest loginParam = om.readValue(request.getInputStream(), UserLoginRequest.class);
            // 인증 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginParam.username(), loginParam.password());
            // 인증
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            return null;
        }
    }// 인증 성공 시
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        // userDetails 추출
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        // JWT 토큰 발급
        TokenResponse tokenResponse = customUserDetailsService.getJwtTokenResponse(userDetails);
        // response body에 access 토큰 DTO 담기

        Cookie jwtCookie = new Cookie("JWT-TOKEN", tokenResponse.token());
        //jwtCookie.setHttpOnly(true); // XSS 공격 방지
        jwtCookie.setPath("/"); // 쿠키의 유효한 경로 설정
        jwtCookie.setMaxAge(60 * 60 * 24); // 쿠키의 만료 시간 설정 (예: 1일)
        jwtCookie.setDomain("localhost:3000");

        System.out.println(jwtCookie);
        System.out.println(jwtCookie.getAttributes());
        System.out.println(jwtCookie.getValue());

        // 응답에 쿠키 추가
        response.addCookie(jwtCookie);
        //responseWriter.setResponse(response, 200, tokenResponse);
    }

}