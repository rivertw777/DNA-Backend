package DNA_Backend.api_server.domain.user.dto.request;

public record VerifyEmailCodeRequest(String email, String code) {
}
