package DNA_Backend.api_server.common.security.auth;

import static DNA_Backend.api_server.domain.user.exception.UserExceptionMessage.USER_NAME_NOT_FOUND;

import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.user.service.UserCacheService;
import DNA_Backend.api_server.domain.user.repository.UserRepository;
import DNA_Backend.api_server.common.exception.DnaApplicationException;
import DNA_Backend.api_server.common.security.jwt.TokenManager;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserCacheService userCacheService;
    private final TokenManager tokenManager;

    // 이름으로 회원 조회
    @Override
    public UserDetailsCustom loadUserByUsername(String username) {
        User user = getUserFromCache(username);
        return new UserDetailsCustom(user);
    }

    private User getUserFromCache(String username) {
        User user = userCacheService.getUserCache(username);
        if (user == null) {
            user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new DnaApplicationException(USER_NAME_NOT_FOUND.getValue()));
            userCacheService.setUserCache(username, user);
        }
        return user;
    }

    // 인증 정보 반환
    public Authentication extractAuthentication(String token) {
        Claims claims = tokenManager.parseClaims(token);
        String username = claims.getSubject();
        UserDetailsCustom userDetails = loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
