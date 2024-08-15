package DNA_Backend.api_server.domain.user.dto;

public class UserDto {
    public record SignUpRequest(String username, String email, String password) {
    }

    public record CheckDuplicateUsernameRequest(String username) {
    }

    public record CheckDuplicateUsernameResponse(boolean isDuplicate) {
    }

    public record UsernameResponse(String username) {
    }

}
