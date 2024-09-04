package DNA_Backend.api_server.domain.location.model.enums;

import static DNA_Backend.api_server.domain.location.message.LocationExceptionMessage.UNVAILD_LOCATION_NAME;

import DNA_Backend.api_server.global.exception.DnaApplicationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LocationName {

    SOCKCHO("sockcho"),
    CHUNCHEON("chuncheon"),
    YANGYANG("yangyang"),
    GANGNEUNG("gangneung"),
    WONJU("wonju"),
    SAMCHEOK("samcheok");

    private final String value;

    public static LocationName fromValue(String value) {
        for (LocationName name : LocationName.values()) {
            if (name.getValue().equalsIgnoreCase(value)) {
                return name;
            }
        }
        throw new DnaApplicationException(UNVAILD_LOCATION_NAME.getValue());
    }

}