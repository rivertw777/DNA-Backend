package TourData.backend.domain.workationSchedule.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkationScheduleExceptionMessage {

    START_DATE_AFTER_END_DATE("시작 날짜는 종료 날짜보다 빨라야 합니다.");

    private final String message;

}