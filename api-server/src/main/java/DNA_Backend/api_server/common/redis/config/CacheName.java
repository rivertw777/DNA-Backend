package DNA_Backend.api_server.common.redis.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheName {

    ALL_LOCATIONS("AllLocations"),
    LOCATION_DETAIL("LocationDetail"),

    ALL_LOCATION_WEATHERS("AllLocationWeathers"),
    LOCATION_WEATHER("LocationWeather"),

    FACILITY_SEARCH_RESULTS("FacilitySearchResults"),
    WORKATION_OFFICE_SEARCH_RESULTS("WorkationOfficeSearchResults"),

    ALL_LOCATION_TOTAL_FACILITY_COUNTS("AllLocationTotalFacilityCounts"),
    LOCATION_TOTAL_FACILITY_COUNT("LocationTotalFacilityCount"),

    All_LOCATION_WORKATION_REVIEWS("AllLocationWorkationReviews"),
    LOCATION_WORKATION_REVIEWS("LocationWorkationReviews");

    private final String value;

}

