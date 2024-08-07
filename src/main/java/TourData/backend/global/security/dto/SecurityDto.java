package TourData.backend.global.security.dto;

public class SecurityDto {
    public record CheckFirstLoginResponse(boolean isFirstLogin) {
    }

    public record NewUsernameRequest(String newUsername) {
    }

    public record UserLoginRequest(String username, String email, String password) {
    }

}
