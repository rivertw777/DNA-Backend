package DNA_Backend.api_server.domain.review.dto;

public class ReviewDto {

    public record WriteReviewRequest(Long locationId, int rating, String content){
    }

}
