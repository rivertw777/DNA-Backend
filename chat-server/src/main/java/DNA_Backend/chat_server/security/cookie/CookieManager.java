package DNA_Backend.chat_server.security.cookie;

import static DNA_Backend.chat_server.security.cookie.CookieProperties.COOKIE_NAME;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class CookieManager {

    public String getTokenFromCookie(HttpServletRequest request) {
        return Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> COOKIE_NAME.getValue().equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    public String getTokenFromCookie(ServerHttpRequest request) {
        return Optional.ofNullable(request.getHeaders().getFirst(HttpHeaders.COOKIE))
                .flatMap(cookieHeader -> Arrays.stream(cookieHeader.split("; "))
                        .filter(cookie -> cookie.startsWith(COOKIE_NAME.getValue() + "="))
                        .map(cookie -> cookie.substring(COOKIE_NAME.getValue().length() + 1))
                        .findFirst())
                .orElse(null);
    }

}
