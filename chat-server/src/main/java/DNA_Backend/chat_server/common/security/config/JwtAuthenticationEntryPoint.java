package DNA_Backend.chat_server.common.security.config;

import static DNA_Backend.chat_server.common.security.exception.SecurityExceptionMessage.UNAUTHORIZED;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import DNA_Backend.chat_server.common.security.utils.ResponseWriter;
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
        responseWriter.setErrorResponse(response, SC_UNAUTHORIZED, UNAUTHORIZED.getValue());
    }
}