package TourData.backend.domain.user.dto;

public class UserDto {
    public record UserSignUpRequest(String username, String email, String password) {
    }

    public record UserNameResponse(String username) {
    }

}
