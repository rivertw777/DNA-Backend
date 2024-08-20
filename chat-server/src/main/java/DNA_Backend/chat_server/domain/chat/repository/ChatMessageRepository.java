package DNA_Backend.chat_server.domain.chat.repository;

import DNA_Backend.chat_server.domain.chat.dto.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
}

