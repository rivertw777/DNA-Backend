package DNA_Backend.api_server.domain.location.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LocationExceptionMessage {

    LOCATION_NOT_FOUND("Location not found."),
    LOCATION_WEATHER_REQUEST_FAILED("Location weather API request failed."),
    INVALID_LOCATION_NAME("Invalid location name."),
    LOCATION_NAME_NOT_FOUND("No location found with the specified name.");

    private final String value;

}
