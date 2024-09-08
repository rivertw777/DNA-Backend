package DNA_Backend.chat_server.domain.user.model;

import java.util.List;
import lombok.Getter;

@Getter
public class UserCache {

    private Long id;

    private String username;

    private String email;

    private String password;

    private List<Role> roles;

}