package DNA_Backend.api_server.domain.recommendedLocation.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecommendedLocationExceptionMessage {

    LOCATION_RECOMMEND_REQUEST_FAILED("Location recommendation API request failed.");

    private final String value;

}
