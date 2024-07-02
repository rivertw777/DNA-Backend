package TourData.backend.domain.chat.dto;

public record ChatRequest(Long roomId, String sender, String message) {
    public ChatRequest {
    }
}