package TourData.backend.domain.facility.model;

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
        throw new IllegalArgumentException("Unknown FacilityType: " + value);
    }

}