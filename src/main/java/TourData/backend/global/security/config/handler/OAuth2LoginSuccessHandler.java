package TourData.backend.global.security.config.handler;

import TourData.backend.global.security.auth.CustomUserDetails;
import TourData.backend.global.security.utils.TokenProvider;
import TourData.backend.global.security.utils.CookieManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final CookieManager cookieManager;
    private final UriProperties uriProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // userDetails 추출
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        // JWT 토큰 발급
        String token = tokenProvider.generateToken(userDetails);
        // 쿠키 저장
        cookieManager.setCookie(response, token);
        String uri = createURI().toString();
        // 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    private URI createURI() {
        return UriComponentsBuilder
                .newInstance()
                .scheme(uriProperties.getScheme())
                .host(uriProperties.getHost())
                .port(uriProperties.getPort())
                .path(uriProperties.getPath())
                .build()
                .toUri();
    }

}