package DNA_Backend.api_server.global.security.auth;

import static DNA_Backend.api_server.domain.user.exception.UserExceptionMessage.USER_NAME_NOT_FOUND;

import DNA_Backend.api_server.domain.user.exception.UserException;
import DNA_Backend.api_server.domain.user.model.User;
import DNA_Backend.api_server.domain.user.repository.UserRepository;
import DNA_Backend.api_server.global.security.jwt.TokenManager;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenManager tokenManager;

    // 이름으로 회원 조회
    @Override
    @Transactional(readOnly = true)
    public UserDetailsCustom loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(USER_NAME_NOT_FOUND.getMessage()));
        return new UserDetailsCustom(user);
    }

    // 인증 정보 반환
    public Authentication extractAuthentication(String token) {
        Claims claims = tokenManager.parseClaims(token);
        String username = claims.getSubject();
        UserDetailsCustom userDetails = loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
