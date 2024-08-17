package DNA_Backend.api_server.global.redis.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CasheName {

    ALL_LOCATIONS("AllLocations"),
    ALL_LOCATION_WEATHER("AllLocationWeathers"),
    ALL_LOCATION_TOTAL_FACILITY_COUNTS("AllLocationTotalFacilityCounts");

    private final String value;

}

