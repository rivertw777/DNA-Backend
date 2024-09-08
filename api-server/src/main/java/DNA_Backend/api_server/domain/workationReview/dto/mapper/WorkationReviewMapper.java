package DNA_Backend.api_server.domain.workationReview.dto.mapper;

import DNA_Backend.api_server.domain.workationReview.dto.response.WorkationReviewResponse;
import DNA_Backend.api_server.domain.workationReview.model.entity.WorkationReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkationReviewMapper {

    @Mapping(target = "reviewId", source = "id")
    @Mapping(target = "username", source = "workationSchedule.user.username")
    @Mapping(target = "locationName", source = "workationSchedule.location.name.value")
    @Mapping(target = "startDate", expression = "java(convertDateToList(workationReview.getWorkationSchedule().getStartDate()))")
    @Mapping(target = "endDate", expression = "java(convertDateToList(workationReview.getWorkationSchedule().getEndDate()))")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "createdAt", expression = "java(convertDateTimeToList(workationReview.getCreatedAt()))")
    WorkationReviewResponse toResponse(WorkationReview workationReview);

    List<WorkationReviewResponse> toResponses(List<WorkationReview> workationReviews);

    default List<Integer> convertDateToList(LocalDate date) {
        return List.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    default List<Integer> convertDateTimeToList(LocalDateTime dateTime) {
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

