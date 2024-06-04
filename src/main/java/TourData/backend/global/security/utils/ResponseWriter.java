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
        setResponse(response, httpStatus, errorResponse);
    }

    public void writeTokenResponse(HttpServletResponse response, TokenResponse tokenResponse)
            throws IOException {
        setResponse(response, 200, tokenResponse);
    }

    public void setResponse(HttpServletResponse response, int httpStatus, Object responseDto) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(responseDto);
        response.setStatus(httpStatus);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }

}
