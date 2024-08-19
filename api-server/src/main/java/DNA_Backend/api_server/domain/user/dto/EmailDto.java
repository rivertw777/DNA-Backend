package DNA_Backend.api_server.domain.user.dto;

public class EmailDto {

    public record SendEmailCodeRequest(String email) {
    }

    public record VerifyEmailCodeRequest(String email, String code) {
    }

    public record VerifyEmailCodeResponse(boolean isVerified) {
    }

    public record UserPopupStatusResponse(String popupStatus) {
    }

}
