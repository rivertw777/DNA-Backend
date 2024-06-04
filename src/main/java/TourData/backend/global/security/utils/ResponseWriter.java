package TourData.backend.global.security.utils;

import TourData.backend.global.exception.dto.CustomErrorResponse;
import TourData.backend.global.security.dto.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class ResponseWriter {
    public void writeErrorResponse(HttpServletResponse response, int httpStatus, CustomErrorResponse errorResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(errorResponse);
        setResponse(response, httpStatus, responseBody);
    }

    public void writeTokenResponse(HttpServletResponse response, TokenResponse tokenResponse)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(tokenResponse);
        setResponse(response, 200, responseBody);
    }

    private void setResponse(HttpServletResponse response, int httpStatus, String responseBody) throws IOException {
        response.setStatus(httpStatus);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }

}
