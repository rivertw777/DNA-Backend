package DNA_Backend.api_server.domain.workationSchedule.dto;

import static DNA_Backend.api_server.domain.workationSchedule.message.WorkationScheduleExceptionMessage.SAME_DATE;
import static DNA_Backend.api_server.domain.workationSchedule.message.WorkationScheduleExceptionMessage.START_DATE_AFTER_END_DATE;

import DNA_Backend.api_server.global.exception.DnaApplicationException;
import java.time.LocalDate;
import java.util.List;

public class WorkationScheduleDto {

    public record CreateWorkationScheduleRequest(LocalDate startDate, LocalDate endDate) {
        public CreateWorkationScheduleRequest {
            validateScheduleDates(startDate, endDate);
        }
        private static void validateScheduleDates(LocalDate startDate, LocalDate endDate) {
            if (startDate.isAfter(endDate)) {
                throw new DnaApplicationException(START_DATE_AFTER_END_DATE.getValue());
            } else if (startDate.isEqual(endDate)) {
                throw new DnaApplicationException(SAME_DATE.getValue());
            }
        }
    }

    public record WorkationScheduleResponse(Long scheduleId, String LocationName, LocalDate startDate, LocalDate endDate,
                                            boolean hasReview){
    }

    public record AllScheduledDatesResponse(List<LocalDate> scheduledDates){
    }

}
