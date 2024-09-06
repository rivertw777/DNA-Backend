package DNA_Backend.api_server.domain.review.dto;

import DNA_Backend.api_server.domain.review.model.entity.Review;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ReviewResponse(
        Long reviewId,
        String username,
        String locationName,
        List<Integer> startDate,
        List<Integer> endDate,
        String content,
        List<Integer> createdAt) {

    public ReviewResponse(Review review) {
        this(
                review.getId(),
                review.getWorkationSchedule().getUser().getUsername(),
                review.getWorkationSchedule().getLocation().getName().getValue(),
                convertDateToList(review.getWorkationSchedule().getStartDate()),
                convertDateToList(review.getWorkationSchedule().getEndDate()),
                review.getContent(),
                convertDateTimeToList(review.getCreatedAt())
        );
    }

    private static List<Integer> convertDateToList(LocalDate date) {
        return List.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    private static List<Integer> convertDateTimeToList(LocalDateTime dateTime) {
        return List.of(
                dateTime.getYear(),
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getHour(),
                dateTime.getMinute(),
                dateTime.getSecond()
        );
    }
}

