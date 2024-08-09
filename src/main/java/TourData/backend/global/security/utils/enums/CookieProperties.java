package TourData.backend.global.security.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CookieProperties {

    COOKIE_NAME("JWT-TOKEN");

    private final String value;

}
