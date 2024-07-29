package TourData.backend.global.webSocket;

import static TourData.backend.global.security.jwt.JwtProperties.COOKIE_NAME;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Component
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 쿠키에서 토큰 추출
        String token = getTokenFromCookie(request);
        if (token == null) {
            return false;
        }

        // 세션에 토큰 저장
        attributes.put(COOKIE_NAME.getValue(), token);
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    private String getTokenFromCookie(ServerHttpRequest request) {
        return Optional.ofNullable(request.getHeaders().getFirst(HttpHeaders.COOKIE))
                .map(cookieHeader -> Arrays.stream(cookieHeader.split("; "))
                        .filter(cookie -> cookie.startsWith(COOKIE_NAME.getValue() + "="))
                        .map(cookie -> cookie.substring(COOKIE_NAME.getValue().length() + 1))
                        .findFirst()
                        .orElse(null))
                .orElse(null);
    }
}