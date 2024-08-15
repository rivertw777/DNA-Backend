package DNA_Backend.api_server.global.security.utils;

import static DNA_Backend.api_server.global.security.utils.CookieProperties.COOKIE_NAME;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class CookieManager {

    public void setCookie(HttpServletResponse response, String token) {
        ResponseCookie jwtCookie = ResponseCookie.from(COOKIE_NAME.getValue(), token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .maxAge(3600)
                .path("/")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
    }

    public void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        Optional.ofNullable(request.getCookies()).ifPresent(cookies ->
                Arrays.stream(cookies)
                        .forEach(cookie -> {
                            cookie.setPath("/");
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }));
    }

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
