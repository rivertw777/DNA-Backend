package DNA_Backend.api_server.global.redis.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CasheName {

    ALL_LOCATIONS("AllLocations"),
    WEATHERS_FOR_ALL_LOCATIONS("WeathersForAllLocations"),
    TOTAL_FACILITY_COUNTS_FOR_ALL_LOCATIONS("TotalFacilityCountsForAllLocations");

    private final String value;

}

