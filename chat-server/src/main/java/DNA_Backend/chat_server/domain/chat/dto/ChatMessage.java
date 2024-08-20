package DNA_Backend.chat_server.domain.chat.dto;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Getter
@Document(collection = "chat_messages")
public class ChatMessage {

    @Id
    private String id;

    private MessageType type;

    private String roomId;

    private String sender;

    private String message;

    @Indexed(expireAfter = "3600s")  // 1시간 후에 삭제
    private LocalDateTime createdAt;

    @Transient
    private long participantCount;

    public void setSender(String sender){
        this.sender = sender;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setParticipantCount(long participantCount) {
        this.participantCount = participantCount;
    }

}