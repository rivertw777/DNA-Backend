package DNA_Backend.chat_server.domain.chat.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatMessage {

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
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