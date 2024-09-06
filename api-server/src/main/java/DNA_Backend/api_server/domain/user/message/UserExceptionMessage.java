package DNA_Backend.api_server.domain.user.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionMessage {

    USER_NOT_FOUND("User not found."),
    DUPLICATE_NAME("Name already exists."),
    USER_NAME_NOT_FOUND("No user found with the specified name.");

    private final String value;

}
