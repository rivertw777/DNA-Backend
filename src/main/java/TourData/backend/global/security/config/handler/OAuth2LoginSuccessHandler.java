package TourData.backend.global.security.config.handler;

import TourData.backend.global.security.auth.CustomUserDetails;
import TourData.backend.global.security.jwt.TokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = tokenProvider.generateToken(userDetails);
        String uri = createURI(token).toString();

        Cookie jwtCookie = new Cookie("JWT-TOKEN", token);
        jwtCookie.setMaxAge(60 * 60 * 24); // 쿠키의 만료 시간 설정 (예: 1일)

        // 응답에 쿠키 추가
        response.addCookie(jwtCookie);

        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    private URI createURI(String token) {
        //MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        //queryParams.add("token", token);
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(3000)
                .path("/")
                //.queryParams(queryParams)
                .build()
                .toUri();
    }

}