package DNA_Backend.chat_server.domain.chat.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
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

    @Indexed
    private LocalDateTime createdAt;

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