package DNA_Backend.chat_server.chat.service;

import DNA_Backend.chat_server.chat.dto.ChatMessage;
import DNA_Backend.chat_server.chat.dto.ChatMessage.MessageType;
import DNA_Backend.chat_server.redis.messaging.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final RedisPublisher redisPublisher;
    private final ParticipantCountService participantCountService;

    public void handleMessage(String username, ChatMessage chatMessage) {
        chatMessage.setSender(username);

        if (MessageType.CHAT.equals(chatMessage.getType())) {
            handleChat(chatMessage);
        } else if (MessageType.JOIN.equals(chatMessage.getType())) {
            handleJoin(chatMessage);
        } else if (MessageType.LEAVE.equals(chatMessage.getType())) {
            handleLeave(chatMessage);
        }

        redisPublisher.publishMessage(chatMessage);
    }

    private void handleChat(ChatMessage chatMessage) {
        int participantCount = participantCountService.getParticipantCount(chatMessage.getRoomId());
        chatMessage.setParticipantCount(participantCount);
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
