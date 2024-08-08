package TourData.backend.domain.workationSchedule.dto;

import static TourData.backend.domain.workationSchedule.exception.WorkationScheduleExceptionMessage.SAME_DATE;
import static TourData.backend.domain.workationSchedule.exception.WorkationScheduleExceptionMessage.START_DATE_AFTER_END_DATE;

import TourData.backend.domain.workationSchedule.exception.WorkationScheduleException;
import java.time.LocalDateTime;

public class WorkationScheduleDto {

    public record WorkationScheduleCreateRequest(String locationName, LocalDateTime startDate, LocalDateTime endDate) {
        public WorkationScheduleCreateRequest {
            validateScheduleDates(startDate, endDate);
        }
        private static void validateScheduleDates(LocalDateTime startDate, LocalDateTime endDate) {
            if (startDate.isAfter(endDate)) {
                throw new WorkationScheduleException(START_DATE_AFTER_END_DATE.getMessage());
            } else if (startDate.isEqual(endDate)) {
                throw new WorkationScheduleException(SAME_DATE.getMessage());
            }
        }
    }

    public record WorkationScheduleResponse(Long id, String LocationName, LocalDateTime startDate, LocalDateTime endDate){
    }

}
