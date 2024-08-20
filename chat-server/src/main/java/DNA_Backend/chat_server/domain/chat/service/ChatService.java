package DNA_Backend.chat_server.domain.chat.service;

import DNA_Backend.chat_server.domain.chat.dto.ChatMessage;

import DNA_Backend.chat_server.domain.chat.dto.ChatRoomMessageResponse;
import DNA_Backend.chat_server.domain.chat.dto.MessageType;
import DNA_Backend.chat_server.domain.chat.repository.ChatMessageRepository;
import DNA_Backend.chat_server.global.redis.messaging.RedisPublisher;
import java.util.List;
import java.util.stream.Collectors;
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

    // 채팅방 메시지 조회
    public List<ChatRoomMessageResponse> getChatMessagesByRoomId(String roomId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findByRoomId(roomId);

        return chatMessages.stream()
                .filter(chatMessage -> chatMessage.getType() == MessageType.CHAT)
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private ChatRoomMessageResponse toResponseDto(ChatMessage chatMessage) {
        return new ChatRoomMessageResponse(
                chatMessage.getSender(),
                chatMessage.getMessage(),
                chatMessage.getCreatedAt()
        );
    }

}
