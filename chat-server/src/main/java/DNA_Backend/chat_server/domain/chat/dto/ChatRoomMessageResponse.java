package DNA_Backend.chat_server.domain.chat.dto;

import java.time.LocalDateTime;

public record ChatRoomMessageResponse(String sender, String message, LocalDateTime createdAt) {
}
