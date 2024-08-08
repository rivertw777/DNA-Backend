package TourData.backend.domain.workationSchedule.dto;

import java.time.LocalDateTime;

public class WorkationScheduleDto {

    public record WorkationScheduleCreateRequest(String LocationName, LocalDateTime startDate, LocalDateTime endDate){
    }

    public record WorkationScheduleResponse(Long id, String LocationName, LocalDateTime startDate, LocalDateTime endDate){
    }

}
