package DNA_Backend.api_server.domain.workationSchedule.dto.response;

import java.time.LocalDate;

public record WorkationScheduleResponse(Long scheduleId, Long locationId, String locationName, String locationThumbnail,
                                        LocalDate startDate, LocalDate endDate, boolean hasReview){
}

