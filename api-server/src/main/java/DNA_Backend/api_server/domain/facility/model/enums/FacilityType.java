package DNA_Backend.api_server.domain.facility.model.enums;

import static DNA_Backend.api_server.domain.facility.message.FacilityExceptionMessage.UNKNOWN_TYPE;

import DNA_Backend.api_server.global.exception.DnaApplicationException;
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
        throw new DnaApplicationException(UNKNOWN_TYPE.getMessage());
    }

}