package TourData.backend.global.security.utils;

import static TourData.backend.global.security.jwt.JwtProperties.COOKIE_NAME;

import TourData.backend.global.dto.CustomErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class ResponseWriter {
    public void setErrorResponse(HttpServletResponse response, int httpStatus, String errorMessage) throws IOException {
        CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(errorResponse);
        response.setStatus(httpStatus);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }

    public void setCookie(HttpServletResponse response, String token) {
        Cookie jwtCookie = new Cookie(COOKIE_NAME.getValue(), token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(-1);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
    }

}
