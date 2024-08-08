package TourData.backend.global.security.config.handler;

import static TourData.backend.global.security.exception.SecurityExceptionMessage.UNAUTHORIZED;
import static com.nimbusds.oauth2.sdk.http.HTTPResponse.SC_UNAUTHORIZED;

import TourData.backend.global.security.utils.ResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

// 인증 실패 (401)
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ResponseWriter responseWriter;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {
        responseWriter.setErrorResponse(response, SC_UNAUTHORIZED, UNAUTHORIZED.getMessage());
    }
}