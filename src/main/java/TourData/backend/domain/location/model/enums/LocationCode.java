package TourData.backend.domain.location.model.enums;

import static TourData.backend.domain.location.exception.LocationExceptionMessage.UNKNOWN_LOCATION_CODE;

import TourData.backend.domain.location.exception.LocationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LocationCode {

    SOCKCHO("01"),
    CHUNCHEON("02"),
    YANGYANG("03"),
    GANGNEUNG("04"),
    TAEBAEK("05"),
    YEONGWOL("06"),
    DONGHAE("07");

    private final String value;

    public static LocationCode fromValue(String value) {
        for (LocationCode code : LocationCode.values()) {
            if (code.getValue().equalsIgnoreCase(value)) {
                return code;
            }
        }
        throw new LocationException(UNKNOWN_LOCATION_CODE.getMessage());
    }

}