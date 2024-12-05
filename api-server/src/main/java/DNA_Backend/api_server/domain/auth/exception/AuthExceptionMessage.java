package DNA_Backend.api_server.domain.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionMessage {

    PASSWORD_NOT_MATCH("Password does not match.");

    private final String value;

}

