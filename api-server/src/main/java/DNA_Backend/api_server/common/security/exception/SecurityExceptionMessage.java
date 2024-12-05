package DNA_Backend.api_server.common.security.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityExceptionMessage {

    UNAUTHORIZED("Authentication is required. Please log in."),
    NO_AUTHORITY("Access is denied.");

    private final String value;

}
