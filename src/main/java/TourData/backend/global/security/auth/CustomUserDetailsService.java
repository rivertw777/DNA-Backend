package TourData.backend.global.security.auth;

import TourData.backend.domain.user.model.entity.User;
import TourData.backend.domain.user.repository.UserRepository;
import TourData.backend.global.security.dto.JwtTokenResponse;
import TourData.backend.global.security.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    // 이름으로 회원 조회
    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 이름을 가진 이용자가 없습니다!"));
        return new CustomUserDetails(user);
    }

    // JWT 토큰 DTO 반환
    public JwtTokenResponse getJwtTokenResponse(CustomUserDetails userDetails) {
        String token = tokenProvider.generateToken(userDetails);
        return new JwtTokenResponse(token);
    }

    // 토큰 검증
    public void validateToken(String token) {
        tokenProvider.validateToken(token);
    }

    // 인증 정보 반환
    public Authentication extractAuthentication(String token) {
        // 토큰 복호화
        Claims claims = tokenProvider.parseClaims(token);
        // 회원 이름 추출
        String userName = claims.getSubject();
        // userDetails 조회
        CustomUserDetails userDetails = loadUserByUsername(userName);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
