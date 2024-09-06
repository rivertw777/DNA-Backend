package DNA_Backend.api_server.global.redis.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheName {

    ALL_LOCATIONS("AllLocations"),
    LOCATION_DETAIL("LocationDetail"),
    ALL_LOCATION_WEATHERS("AllLocationWeathers"),
    LOCATION_WEATHER("LocationWeather"),
    ALL_LOCATION_TOTAL_FACILITY_COUNTS("AllLocationTotalFacilityCounts"),
    LOCATION_TOTAL_FACILITY_COUNT("LocationTotalFacilityCount"),
    All_WORKATION_REVIEWS("AllWorkationReviews"),
    LOCATION_WORKATION_REVIEWS("LocationWorkationReviews");

    private final String value;

}

