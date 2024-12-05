package DNA_Backend.api_server.common.security.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtExceptionMessage {

    INVALID_TOKEN("Invalid token."),
    EXPIRED_TOKEN("Expired token.");

    private final String value;

}