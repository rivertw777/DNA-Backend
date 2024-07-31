package TourData.backend.global.redis.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CasheName {

    LOCATION("location"),
    LOCATION_WEATHER("LocationWeather");

    private final String value;

}

