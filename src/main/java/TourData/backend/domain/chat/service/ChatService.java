package TourData.backend.domain.chat.service;

import TourData.backend.domain.chat.dto.ChatMessage;
import TourData.backend.global.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final RedisPublisher redisPublisher;

    public void handleMessage(String username, ChatMessage chatMessage) {
        chatMessage.setSender(username);
        if (ChatMessage.MessageType.JOIN.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
        } else if (ChatMessage.MessageType.LEAVE.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장하셨습니다.");
        }
        redisPublisher.publishMessage(chatMessage);
    }

}
