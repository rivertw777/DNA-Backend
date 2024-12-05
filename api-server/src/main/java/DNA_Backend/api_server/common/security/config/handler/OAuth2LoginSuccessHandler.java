package DNA_Backend.api_server.common.security.config.handler;

import DNA_Backend.api_server.common.security.auth.UserDetailsCustom;
import DNA_Backend.api_server.common.security.cookie.CookieManager;
import DNA_Backend.api_server.common.security.jwt.TokenManager;
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

    private final TokenManager tokenManager;
    private final CookieManager cookieManager;
    private final UriProperties uriProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetailsCustom userDetails = (UserDetailsCustom) authentication.getPrincipal();
        String token = tokenManager.generateToken(userDetails);
        cookieManager.setCookie(response, token);
        String uri = createURI().toString();
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