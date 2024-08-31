package DNA_Backend.api_server.domain.recommendation.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecommendationExceptionMessage {

    RECOMMEND_REQUEST_FAILED("요청에 실패하였습니다.");

    private final String message;

}