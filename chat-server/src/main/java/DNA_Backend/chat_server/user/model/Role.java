package DNA_Backend.chat_server.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

}