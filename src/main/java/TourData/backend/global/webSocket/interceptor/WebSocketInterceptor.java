package TourData.backend.global.webSocket.interceptor;

import static TourData.backend.global.security.util.CookieProperties.COOKIE_NAME;

import TourData.backend.global.security.auth.CustomUserDetailsService;
import TourData.backend.global.security.jwt.TokenProvider;
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
    private final TokenProvider tokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor.getCommand() == StompCommand.CONNECT) {
            String token = (String) accessor.getSessionAttributes().get(COOKIE_NAME.getValue());
            tokenProvider.validateToken(token);
            Authentication authentication = customUserDetailsService.extractAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return message;
    }

}
