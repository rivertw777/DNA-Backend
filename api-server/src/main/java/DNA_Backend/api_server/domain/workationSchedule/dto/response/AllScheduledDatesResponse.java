package DNA_Backend.api_server.domain.workationSchedule.dto.response;

import java.time.LocalDate;
import java.util.List;

public record AllScheduledDatesResponse(
        List<LocalDate> scheduledDates
){
}