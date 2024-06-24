package TourData.backend.domain.location.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LocationExceptionMessage {

    LOCATION_NOT_FOUND("해당하는 지역이 없습니다.");

    private final String message;

}