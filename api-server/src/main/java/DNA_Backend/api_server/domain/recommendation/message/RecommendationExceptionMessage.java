package DNA_Backend.api_server.domain.recommendation.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecommendationExceptionMessage {

    LOCATION_RECOMMEND_REQUEST_FAILED("Location recommendation API request failed.");

    private final String value;

}
