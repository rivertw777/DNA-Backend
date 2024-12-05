package DNA_Backend.chat_server.common.security.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityExceptionMessage {

    UNAUTHORIZED("Authentication is required. Please log in.");

    private final String value;

}
