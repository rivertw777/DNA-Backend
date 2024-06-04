package TourData.backend.global.security.config.handler;

import static TourData.backend.global.security.exception.AuthExceptionMessage.NO_AUTHORITY;
import static com.nimbusds.oauth2.sdk.http.HTTPResponse.SC_FORBIDDEN;

import TourData.backend.global.exception.dto.CustomErrorResponse;
import TourData.backend.global.security.utils.ResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

// 인증이 되었지만 접근 권한이 없는 경우 (403)
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ResponseWriter responseWriter;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        CustomErrorResponse errorResponse = new CustomErrorResponse(NO_AUTHORITY.getMessage());
        responseWriter.setResponse(response, SC_FORBIDDEN, errorResponse);
    }

}
