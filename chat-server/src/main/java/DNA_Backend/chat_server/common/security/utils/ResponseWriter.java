package DNA_Backend.chat_server.common.security.utils;

import DNA_Backend.chat_server.common.dto.ErrorResponseCustom;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class ResponseWriter {

    public void setErrorResponse(HttpServletResponse response, int httpStatus, String errorMessage) throws IOException {
        response.setStatus(httpStatus);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ErrorResponseCustom errorResponse = new ErrorResponseCustom(errorMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(responseBody);
    }

}
