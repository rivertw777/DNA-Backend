package DNA_Backend.api_server.domain.workationSchedule.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkationScheduleExceptionMessage {

    START_DATE_AFTER_END_DATE("시작 날짜는 종료 날짜보다 빨라야 합니다."),
    SAME_DATE("시작 날짜와 종료 날짜가 동일할 수 없습니다."),
    OVERLAPPING_SCHEDULE("해당 기간에 이미 등록된 일정이 있습니다."),
    SCHEDULE_NOT_FOUND("해당하는 일정이 없습니다.");

    private final String message;

}