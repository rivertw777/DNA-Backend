package DNA_Backend.api_server.global.security.cookie;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CookieProperties {

    COOKIE_NAME("JWT-TOKEN");

    private final String value;

}
