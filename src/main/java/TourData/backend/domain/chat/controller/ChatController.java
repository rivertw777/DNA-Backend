package TourData.backend.domain.chat.controller;

import TourData.backend.domain.chat.dto.ChatRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/messages")
    public void sendMessage(@Valid @Payload ChatRequest requestParam) {
        String destination = String.format("/sub/rooms/%s", requestParam.roomId());
        messagingTemplate.convertAndSend(destination, requestParam);
    }


}