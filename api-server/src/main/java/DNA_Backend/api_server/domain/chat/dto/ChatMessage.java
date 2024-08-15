package DNA_Backend.api_server.domain.chat.dto;

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
    private int participantCount;

    public void setSender(String sender){
        this.sender = sender;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

}