package TourData.backend.global.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtProperties {

    HEADER_STRING("Authorization"),
    TOKEN_PREFIX("Bearer ");

    private final String value;

}
