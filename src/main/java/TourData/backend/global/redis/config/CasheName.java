package TourData.backend.global.redis.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CasheName {

    LOCATION("Location"),
    LOCATION_WEATHER("LocationWeather"),
    LOCATION_FACILITIES_COUNT("LocationFacilitiesCount");

    private final String value;

}

