package DNA_Backend.api_server.domain.chat.controller;

import DNA_Backend.api_server.domain.chat.dto.ChatMessage;
import DNA_Backend.api_server.domain.chat.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/messages")
    public void sendMessage(@AuthenticationPrincipal(expression = "username") String username,
                            @Valid @Payload ChatMessage chatMessage) {
        chatService.handleMessage(username, chatMessage);
    }

}