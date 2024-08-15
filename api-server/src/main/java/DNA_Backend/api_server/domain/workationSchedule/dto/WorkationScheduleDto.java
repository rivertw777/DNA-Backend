package DNA_Backend.api_server.domain.workationSchedule.dto;

import static DNA_Backend.api_server.domain.workationSchedule.exception.WorkationScheduleExceptionMessage.SAME_DATE;
import static DNA_Backend.api_server.domain.workationSchedule.exception.WorkationScheduleExceptionMessage.START_DATE_AFTER_END_DATE;

import DNA_Backend.api_server.domain.workationSchedule.exception.WorkationScheduleException;
import java.time.LocalDate;

public class WorkationScheduleDto {

    public record CreateWorkationScheduleRequest(LocalDate startDate, LocalDate endDate) {
        public CreateWorkationScheduleRequest {
            validateScheduleDates(startDate, endDate);
        }
        private static void validateScheduleDates(LocalDate startDate, LocalDate endDate) {
            if (startDate.isAfter(endDate)) {
                throw new WorkationScheduleException(START_DATE_AFTER_END_DATE.getMessage());
            } else if (startDate.isEqual(endDate)) {
                throw new WorkationScheduleException(SAME_DATE.getMessage());
            }
        }
    }

    public record WorkationScheduleResponse(Long scheduleId, String LocationName, LocalDate startDate, LocalDate endDate){
    }

}
