package DNA_Backend.api_server.domain.workationReview.dto;

public class WorkationReviewDto {

    public record WriteWorkationReviewRequest(Long locationId, int rating, String content){
    }

}
