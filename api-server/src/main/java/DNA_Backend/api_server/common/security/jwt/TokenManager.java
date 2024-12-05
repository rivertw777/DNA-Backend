package DNA_Backend.api_server.common.security.jwt;

import static DNA_Backend.api_server.common.security.exception.JwtExceptionMessage.EXPIRED_TOKEN;
import static DNA_Backend.api_server.common.security.exception.JwtExceptionMessage.INVALID_TOKEN;

import DNA_Backend.api_server.common.security.auth.UserDetailsCustom;
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
public class TokenManager {

    private final Key jwtSecretKey;
    private final Long accessTokenExpiration;

    public TokenManager(@Value("${jwt.secret}") String secretKey,
                        @Value("${jwt.access.expiration}") String accessTokenExpiration) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = Long.valueOf(accessTokenExpiration);
    }

    // 토큰 생성
    public String generateToken(UserDetailsCustom userDetails) {
        long now = (new Date()).getTime();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(now + accessTokenExpiration))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
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