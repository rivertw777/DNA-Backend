package DNA_Backend.api_server.domain.workationReview.dto;

import DNA_Backend.api_server.domain.workationReview.model.entity.WorkationReview;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record WorkationReviewResponse(
        Long reviewId,
        String username,
        String locationName,
        List<Integer> startDate,
        List<Integer> endDate,
        String content,
        List<Integer> createdAt) {

    public WorkationReviewResponse(WorkationReview workationReview) {
        this(
                workationReview.getId(),
                workationReview.getWorkationSchedule().getUser().getUsername(),
                workationReview.getWorkationSchedule().getLocation().getName().getValue(),
                convertDateToList(workationReview.getWorkationSchedule().getStartDate()),
                convertDateToList(workationReview.getWorkationSchedule().getEndDate()),
                workationReview.getContent(),
                convertDateTimeToList(workationReview.getCreatedAt())
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

