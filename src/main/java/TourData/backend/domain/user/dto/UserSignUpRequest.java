package TourData.backend.domain.user.dto;

public record UserSignUpRequest(String username, String email, String password) {
    public UserSignUpRequest {
    }
}

