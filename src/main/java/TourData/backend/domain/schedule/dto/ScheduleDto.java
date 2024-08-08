package TourData.backend.domain.schedule.dto;

import java.time.LocalDateTime;

public class ScheduleDto {

    public record ScheduleCreateRequest(String locationName, LocalDateTime startDate, LocalDateTime endDate){
    }

}
