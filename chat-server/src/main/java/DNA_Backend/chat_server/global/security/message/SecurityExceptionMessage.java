package DNA_Backend.chat_server.global.security.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityExceptionMessage {

    UNAUTHORIZED("Authentication is required. Please log in.");

    private final String value;

}
