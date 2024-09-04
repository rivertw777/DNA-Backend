package DNA_Backend.chat_server.global.security.auth;

import DNA_Backend.chat_server.domain.user.model.UserCache;
import DNA_Backend.chat_server.domain.user.repository.UserCacheRepository;
import DNA_Backend.chat_server.global.security.jwt.TokenManager;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UserCacheRepository userCacheRepository;
    private final TokenManager tokenManager;

    @Override
    public UserDetailsCustom loadUserByUsername(String username) {
        UserCache user = userCacheRepository.getUserCache(username);
        return new UserDetailsCustom(user);
    }

    public Authentication extractAuthentication(String token) {
        Claims claims = tokenManager.parseClaims(token);
        String username = claims.getSubject();
        UserDetailsCustom userDetails = loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
