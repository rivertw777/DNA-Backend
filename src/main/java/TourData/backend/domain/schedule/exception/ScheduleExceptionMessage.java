package TourData.backend.domain.schedule.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleExceptionMessage {

    START_DATE_AFTER_END_DATE("시작 날짜는 종료 날짜보다 빨라야 합니다.");

    private final String message;

}