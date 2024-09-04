package DNA_Backend.chat_server.domain.user.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCache {

    private String id;

    private String username;

    private String email;

    private String password;

    private List<Role> roles;

}