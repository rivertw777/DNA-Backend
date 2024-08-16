package DNA_Backend.api_server.global.security.dto;

public class SecurityDto {

    public record LoginRequest(String username, String email, String password) {
    }

    public record CheckFirstLoginResponse(boolean isFirstLogin) {
    }

    public record SetNewUsernameRequest(String newUsername) {
    }

}
