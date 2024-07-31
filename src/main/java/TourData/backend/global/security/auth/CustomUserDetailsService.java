package TourData.backend.global.security.auth;

import static TourData.backend.domain.user.exception.UserExceptionMessage.USER_NAME_NOT_FOUND;

import TourData.backend.domain.user.exception.UserException;
import TourData.backend.domain.user.model.User;
import TourData.backend.domain.user.repository.UserRepository;
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
                .orElseThrow(() -> new UserException(USER_NAME_NOT_FOUND.getMessage()));
        return new CustomUserDetails(user);
    }

    // 인증 정보 반환
    public Authentication extractAuthentication(String token) {
        // 토큰 복호화
        Claims claims = tokenProvider.parseClaims(token);
        // 회원 이름 추출
        String username = claims.getSubject();
        // userDetails 조회
        CustomUserDetails userDetails = loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
