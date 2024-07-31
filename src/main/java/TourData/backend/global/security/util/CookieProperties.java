package TourData.backend.global.security.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CookieProperties {

    COOKIE_NAME("JWT-TOKEN");

    private final String value;

}
