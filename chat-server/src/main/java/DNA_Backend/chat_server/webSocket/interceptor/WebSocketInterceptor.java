package DNA_Backend.chat_server.webSocket.interceptor;

import static DNA_Backend.chat_server.security.cookie.CookieProperties.COOKIE_NAME;

import DNA_Backend.chat_server.security.auth.CustomUserDetailsService;
import DNA_Backend.chat_server.security.jwt.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {

    private final CustomUserDetailsService customUserDetailsService;
    private final TokenManager tokenManager;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor.getCommand() == StompCommand.CONNECT) {
            String token = (String) accessor.getSessionAttributes().get(COOKIE_NAME.getValue());
            tokenManager.validateToken(token);
            Authentication authentication = customUserDetailsService.extractAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return message;
    }

}
