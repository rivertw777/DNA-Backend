package TourData.backend.domain.chat.service;

import TourData.backend.domain.chat.dto.ChatMessage;
import TourData.backend.global.redis.chat.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final RedisPublisher redisPublisher;
    private final ParticipantCountService participantCountService;

    public void handleMessage(String username, ChatMessage chatMessage) {
        chatMessage.setSender(username);

        if (ChatMessage.MessageType.JOIN.equals(chatMessage.getType())) {
            handleJoin(chatMessage);
        } else if (ChatMessage.MessageType.LEAVE.equals(chatMessage.getType())) {
            handleLeave(chatMessage);
        }

        redisPublisher.publishMessage(chatMessage);
    }

    private void handleJoin(ChatMessage chatMessage) {
        chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
        int participantCount = participantCountService.increaseParticipantCount(chatMessage.getRoomId());
        chatMessage.setParticipantCount(participantCount);
    }

    private void handleLeave(ChatMessage chatMessage) {
        chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장하셨습니다.");
        int participantCount = participantCountService.decreaseParticipantCount(chatMessage.getRoomId());
        chatMessage.setParticipantCount(participantCount);
    }

}
