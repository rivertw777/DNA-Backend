package DNA_Backend.api_server.domain.workationReview.dto.response;

import java.util.List;

public record WorkationReviewResponse(
        Long reviewId,
        String username,
        String locationName,
        List<Integer> startDate,
        List<Integer> endDate,
        String content,
        List<Integer> createdAt) {
}

