package TourData.backend.domain.chat.dto;

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

    public void setSender(String sender){
        this.sender = sender;
    }

    public void setMessage(String message){
        this.message = message;
    }

}