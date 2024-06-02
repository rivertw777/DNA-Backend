package TourData.backend.global.security.dto;

public record UserLoginRequest(String username, String email, String password) {
}
