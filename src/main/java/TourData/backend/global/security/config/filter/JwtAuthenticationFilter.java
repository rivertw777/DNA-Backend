package TourData.backend.global.security.config.filter;

import TourData.backend.global.security.auth.CustomUserDetails;
import TourData.backend.global.security.dto.AuthDto.UserLoginRequest;
import TourData.backend.global.security.jwt.TokenProvider;
import TourData.backend.global.security.util.CookieManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
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
    private final TokenProvider tokenProvider;
    private final CookieManager cookieManager;

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
    }

    // 인증 성공 시
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        // userDetails 추출
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        // JWT 토큰 발급
        String token = tokenProvider.generateToken(userDetails);
        // 쿠키 저장
        cookieManager.setCookie(response, token);
    }

}