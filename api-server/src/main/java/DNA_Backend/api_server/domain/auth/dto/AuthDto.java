package DNA_Backend.api_server.domain.auth.dto;

public class AuthDto {

    public record CheckFirstSocialLoginResponse(boolean isFirstLogin) {
    }

    public record UpdateUsernameRequest(String newUsername) {
    }

}
