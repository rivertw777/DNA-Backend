package DNA_Backend.chat_server.domain.chat.service;

import DNA_Backend.chat_server.domain.chat.dto.ChatMessage;

import DNA_Backend.chat_server.domain.chat.dto.MessageType;
import DNA_Backend.chat_server.domain.chat.repository.ChatMessageRepository;
import DNA_Backend.chat_server.global.redis.messaging.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final RedisPublisher redisPublisher;
    private final ParticipantCountService participantCountService;
    private final ChatMessageRepository chatMessageRepository;

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
        chatMessageRepository.save(chatMessage);
    }

    private void handleChat(ChatMessage chatMessage) {
        long participantCount = participantCountService.getParticipantCount(chatMessage.getRoomId());
        chatMessage.setParticipantCount(participantCount);
    }

    private void handleJoin(ChatMessage chatMessage) {
        chatMessage.setMessage(chatMessage.getSender() + " has joined the chat.");
        long participantCount = participantCountService.increaseParticipantCount(chatMessage.getRoomId());
        chatMessage.setParticipantCount(participantCount);
    }

    private void handleLeave(ChatMessage chatMessage) {
        chatMessage.setMessage(chatMessage.getSender() + " has left the chat.");
        long participantCount = participantCountService.decreaseParticipantCount(chatMessage.getRoomId());
        chatMessage.setParticipantCount(participantCount);
    }

}
