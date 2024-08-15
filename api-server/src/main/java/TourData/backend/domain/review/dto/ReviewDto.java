package TourData.backend.domain.review.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReviewDto {

    public record WriteReviewRequest(String content){
    }

    public record ReviewResponse(Long reviewId, String username, String locationName, LocalDate startDate, LocalDate endDate,
                                 String content, LocalDateTime createdAt) {
    }

}
