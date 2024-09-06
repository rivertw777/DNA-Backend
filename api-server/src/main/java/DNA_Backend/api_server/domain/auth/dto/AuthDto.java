package DNA_Backend.api_server.domain.auth.dto;

public class AuthDto {

    public record LoginRequest(String username, String password) {
    }

    public record CheckFirstSocialLoginResponse(boolean isFirstLogin) {
    }

    public record UpdateUsernameRequest(String newUsername) {
    }

}
