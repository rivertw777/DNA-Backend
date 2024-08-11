package TourData.backend.domain.location.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LocationExceptionMessage {

    LOCATION_NOT_FOUND("해당하는 지역이 없습니다."),
    ALREADY_LIKE("이미 좋아요를 눌렀습니다."),
    ALREADY_UNLIKE("이미 좋아요를 취소하였습니다."),
    UNVAILD_LOCATION_NAME("유효하지 않은 지역 이름입니다."),
    LOCATION_NAME_NOT_FOUND("해당하는 이름을 가진 지역이 없습니다.");

    private final String message;

}