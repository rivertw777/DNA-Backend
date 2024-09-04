package DNA_Backend.chat_server.global.security.jwt;

import static DNA_Backend.chat_server.global.security.message.JwtExceptionMessage.EXPIRED_TOKEN;
import static DNA_Backend.chat_server.global.security.message.JwtExceptionMessage.INVALID_TOKEN;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenManager {

    private final Key jwtSecretKey;

    public TokenManager(@Value("${jwt.secret}") String secretKey) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
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
            throw new JwtException(EXPIRED_TOKEN.getValue());
        } catch (MalformedJwtException | UnsupportedJwtException | SignatureException e) {
            throw new JwtException(INVALID_TOKEN.getValue());
        }
    }
    
}