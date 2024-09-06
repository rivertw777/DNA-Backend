package DNA_Backend.api_server.domain.workationSchedule.dto.request;

public record WriteWorkationReviewRequest(Long locationId, int rating, String content){
}
