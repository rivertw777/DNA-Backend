package TourData.backend.global.security.dto;

public class SecurityDto {
    public record CheckFirstLoginResponse(boolean isFirstLogin) {
    }

    public record SetNewUsernameRequest(String newUsername) {
    }

    public record LoginRequest(String username, String email, String password) {
    }

}
