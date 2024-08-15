package DNA_Backend.chat_server.global.security.config.utils;

import static DNA_Backend.chat_server.global.security.exception.JwtExceptionMessage.EXPIRED_TOKEN;
import static DNA_Backend.chat_server.global.security.exception.JwtExceptionMessage.INVALID_TOKEN;

import DNA_Backend.chat_server.global.security.config.auth.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private final Key jwtSecretKey;
    private final Long accessTokenExpiration;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.access.expiration}") String accessTokenExpiration) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = Long.valueOf(accessTokenExpiration);
    }

    // 토큰 복호화
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 검증
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new JwtException(EXPIRED_TOKEN.getMessage());
        } catch (MalformedJwtException | UnsupportedJwtException | SignatureException e) {
            throw new JwtException(INVALID_TOKEN.getMessage());
        }
    }
    
}