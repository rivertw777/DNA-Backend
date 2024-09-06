package DNA_Backend.api_server.domain.workationSchedule.dto.response;

import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
import java.time.LocalDate;

public record WorkationScheduleResponse(Long scheduleId, Long locationId, String LocationName, LocalDate startDate, LocalDate endDate,
                                        boolean hasReview){
    public WorkationScheduleResponse(WorkationSchedule workationSchedule) {
        this(
                workationSchedule.getId(),
                workationSchedule.getLocation().getId(),
                workationSchedule.getLocation().getName().getValue(),
                workationSchedule.getStartDate(),
                workationSchedule.getEndDate(),
                workationSchedule.getWorkationReview() != null
        );
    }
}

