package DNA_Backend.api_server.domain.location.model.enums;

import static DNA_Backend.api_server.domain.location.message.LocationExceptionMessage.INVALID_LOCATION_NAME;

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
    YEONGWOL("yeongwol"),
    SAMCHEOK("samcheok"),
    HONGCHEON("hongcheon"),
    HOENGSEONG("hoengseong"),
    JEONGSEON("jeongseon"),
    INJE("inje"),
    GOSEONG("goseong"),
    PYEONGCHANG("pyeongchang");

    private final String value;

    public static LocationName fromValue(String value) {
        for (LocationName name : LocationName.values()) {
            if (name.getValue().equalsIgnoreCase(value)) {
                return name;
            }
        }
        throw new DnaApplicationException(INVALID_LOCATION_NAME.getValue());
    }

}
