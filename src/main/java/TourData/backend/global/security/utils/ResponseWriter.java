package TourData.backend.global.security.utils;

import static TourData.backend.global.security.jwt.JwtProperties.COOKIE_NAME;

import TourData.backend.global.dto.CustomErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class ResponseWriter {

    public void setErrorResponse(HttpServletResponse response, int httpStatus, String errorMessage) throws IOException {
        response.setStatus(httpStatus);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(responseBody);
    }

    public void setCookie(HttpServletResponse response, String token) {
        ResponseCookie jwtCookie = ResponseCookie.from(COOKIE_NAME.getValue(), token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .maxAge(3600)
                .path("/")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
    }

    public void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        Optional.ofNullable(request.getCookies()).ifPresent(cookies ->
                Arrays.stream(cookies)
                        .forEach(cookie -> {
                            cookie.setPath("/");
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }));
    }

}
