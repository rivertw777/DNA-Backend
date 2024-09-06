package DNA_Backend.api_server.domain.location.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LocationExceptionMessage {

    LOCATION_NOT_FOUND("해당하는 지역이 없습니다."),
    LOCATION_WEATHER_REQUEST_FAILED("지역 날씨 조회 API 요청이 실패하였습니다."),
    INVALID_LOCATION_NAME("유효하지 않은 지역 이름입니다."),
    LOCATION_NAME_NOT_FOUND("해당하는 이름을 가진 지역이 없습니다.");

    private final String value;

}