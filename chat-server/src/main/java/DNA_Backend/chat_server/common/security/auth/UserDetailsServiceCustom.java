package DNA_Backend.chat_server.common.security.auth;

import DNA_Backend.chat_server.domain.user.model.UserCache;
import DNA_Backend.chat_server.domain.user.service.UserCacheService;
import DNA_Backend.chat_server.common.security.jwt.TokenManager;
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

    private final UserCacheService userCacheService;
    private final TokenManager tokenManager;

    @Override
    public UserDetailsCustom loadUserByUsername(String username) {
        UserCache user = userCacheService.getUserCache(username);
        return new UserDetailsCustom(user);
    }

    public Authentication extractAuthentication(String token) {
        Claims claims = tokenManager.parseClaims(token);
        String username = claims.getSubject();
        UserDetailsCustom userDetails = loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
