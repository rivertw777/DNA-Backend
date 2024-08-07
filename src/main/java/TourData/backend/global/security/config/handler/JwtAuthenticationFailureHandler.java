package TourData.backend.global.security.config.handler;

import static TourData.backend.global.security.exception.SecurityExceptionMessage.LOGIN_FAILED;
import static com.nimbusds.oauth2.sdk.http.HTTPResponse.SC_UNAUTHORIZED;

import TourData.backend.global.security.utils.ResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

// 로그인 실패 (401)
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ResponseWriter responseWriter;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException authenticationException) throws IOException {
        responseWriter.setErrorResponse(response, SC_UNAUTHORIZED, LOGIN_FAILED.getMessage());
    }
}
