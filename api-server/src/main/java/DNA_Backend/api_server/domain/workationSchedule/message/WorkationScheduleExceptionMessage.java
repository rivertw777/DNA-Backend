package DNA_Backend.api_server.domain.workationSchedule.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkationScheduleExceptionMessage {

    START_DATE_AFTER_END_DATE("Start date must be before end date."),
    SAME_DATE("Start date and end date cannot be the same."),
    OVERLAPPING_SCHEDULE("There is already a scheduled event during this period."),
    SCHEDULE_NOT_FOUND("Schedule not found.");

    private final String value;

}
