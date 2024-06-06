package TourData.backend.global.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtProperties {

    COOKIE_NAME("JWT-TOKEN");

    private final String value;

}
