package TourData.backend.domain.facility.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FacilityExceptionMessage {

    FACILITY_NOT_FOUND("해당하는 시설이 없습니다."),
    ALREADY_BOOKMARK("이미 북마크를 하였습니다."),
    ALREADY_UNBOOKMARK("이미 북마크를 취소하였습니다."),
    UNKNOWN_TYPE("알 수 없는 시설 유형입니다.");

    private final String message;

}
