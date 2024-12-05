package DNA_Backend.api_server.domain.auth.dto.response;

public record CheckFirstSocialLoginResponse(
        boolean isFirstLogin
) {
}