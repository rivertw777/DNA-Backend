package DNA_Backend.chat_server.domain.chat.controller;

import DNA_Backend.chat_server.domain.chat.dto.ChatMessage;
import DNA_Backend.chat_server.domain.chat.dto.ChatRoomMessageResponse;
import DNA_Backend.chat_server.domain.chat.service.ChatService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/messages")
    public void sendMessage(@AuthenticationPrincipal(expression = "username") String username,
                            @Valid @Payload ChatMessage chatMessage) {
        chatService.handleMessage(username, chatMessage);
    }

    // 채팅방 메시지 조회
    @GetMapping("/api/chat/{roomId}")
    @ResponseBody
    public ResponseEntity<List<ChatRoomMessageResponse>> getChatMessagesByRoomId(@PathVariable("roomId") String roomId) {
        List<ChatRoomMessageResponse> reponses = chatService.getChatMessagesByRoomId(roomId);
        return ResponseEntity.ok(reponses);
    }

}