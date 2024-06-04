package TourData.backend.global.security.config.handler;

import static TourData.backend.global.security.exception.AuthExceptionMessage.UNAUTHORIZED;
import static com.nimbusds.oauth2.sdk.http.HTTPResponse.SC_UNAUTHORIZED;

import TourData.backend.global.exception.dto.CustomErrorResponse;
import TourData.backend.global.security.utils.ResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

// 인증 실패 (401)
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ResponseWriter responseWriter;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {
        //CustomErrorResponse errorResponse = new CustomErrorResponse(UNAUTHORIZED.getMessage());
        //responseWriter.writeErrorResponse(response, SC_UNAUTHORIZED, errorResponse);
    }
}