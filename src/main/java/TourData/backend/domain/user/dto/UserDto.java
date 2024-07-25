package TourData.backend.domain.user.dto;

public class UserDto {
    public record UserSignUpRequest(String username, String email, String password) {
    }

    public record ValidateDuplicateUsernameRequest(String username) {
    }

    public record ValidateDuplicateUsernameResponse(boolean isDuplicated) {
    }

    public record UsernameResponse(String username) {
    }

}
