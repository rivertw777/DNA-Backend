package TourData.backend.domain.review.dto;

import java.time.LocalDateTime;

public class ReviewDto {

    public record ReviewWriteRequest(Long scheduleId, String content){
    }

    public record ReviewResponse(String username, String locationName, LocalDateTime startDate, LocalDateTime endDate,
                                 String content, LocalDateTime createdAt) {
    }

}
