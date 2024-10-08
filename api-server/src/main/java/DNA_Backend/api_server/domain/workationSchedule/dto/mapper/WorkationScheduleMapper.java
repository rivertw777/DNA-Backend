package DNA_Backend.api_server.domain.workationSchedule.dto.mapper;

import DNA_Backend.api_server.domain.workationSchedule.dto.response.WorkationScheduleResponse;
import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkationScheduleMapper {

    @Mapping(target = "scheduleId", source = "id")
    @Mapping(target = "locationId", source = "location.id")
    @Mapping(target = "locationName", source = "location.name.value")
    @Mapping(target = "locationThumbnail", source = "location.thumbnail")
    @Mapping(target = "hasReview", expression = "java(hasReview(workationSchedule))")
    WorkationScheduleResponse toResponse(WorkationSchedule workationSchedule);

    List<WorkationScheduleResponse> toResponses(List<WorkationSchedule> workationSchedules);

    default boolean hasReview(WorkationSchedule workationSchedule) {
        return workationSchedule.getWorkationReview() != null;
    }
}
