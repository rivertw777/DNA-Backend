package TourData.backend.domain.user.dto;

public class EmailDto {

    public record SendCodeRequest(String email) {
    }

    public record VerifyCodeRequest(String email, String code) {
    }

    public record EmailVerificationResponse(boolean isVerified) {
    }

}
