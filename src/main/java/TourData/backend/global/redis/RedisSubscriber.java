package TourData.backend.global.redis;

import TourData.backend.domain.chat.dto.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    public void sendMessage(String publishMessage) {
        try {
            ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            String destination = String.format("/sub/rooms/%s", chatMessage.getRoomId());
            messagingTemplate.convertAndSend(destination, chatMessage);
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }
}
