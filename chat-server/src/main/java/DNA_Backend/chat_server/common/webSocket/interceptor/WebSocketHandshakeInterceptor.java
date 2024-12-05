package DNA_Backend.chat_server.common.webSocket.interceptor;

import static DNA_Backend.chat_server.common.security.cookie.CookieProperties.COOKIE_NAME;

import DNA_Backend.chat_server.common.security.cookie.CookieManager;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Component
@RequiredArgsConstructor
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private final CookieManager cookieManager;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 쿠키에서 토큰 추출
        String token = cookieManager.getTokenFromCookie(request);
        if (token == null) {
            return false;
        }
        // 세션에 토큰 저장
        attributes.put(COOKIE_NAME.getValue(), token);
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

}