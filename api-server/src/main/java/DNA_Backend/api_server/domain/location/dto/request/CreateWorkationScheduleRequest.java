package DNA_Backend.api_server.domain.location.dto.request;

import static DNA_Backend.api_server.domain.workationSchedule.exception.WorkationScheduleExceptionMessage.SAME_DATE;
import static DNA_Backend.api_server.domain.workationSchedule.exception.WorkationScheduleExceptionMessage.START_DATE_AFTER_END_DATE;

import DNA_Backend.api_server.common.exception.DnaApplicationException;
import java.time.LocalDate;

public record CreateWorkationScheduleRequest(
        LocalDate startDate,
        LocalDate endDate
) {
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