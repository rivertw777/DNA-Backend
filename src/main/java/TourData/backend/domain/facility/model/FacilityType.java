package TourData.backend.domain.facility.model;

import static TourData.backend.domain.facility.exception.FacilityExceptionMessage.UNKNOWN_TYPE;

import TourData.backend.domain.facility.exception.FacilityException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FacilityType {

    WORKATION("workation"),
    CAFE("cafe"),
    RESTAURANT("restaurant"),
    ACCOMMODATION("accommodation");

    private final String value;

    public static FacilityType fromValue(String value) {
        for (FacilityType type : FacilityType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new FacilityException(UNKNOWN_TYPE + value);
    }

}