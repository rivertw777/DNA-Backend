package DNA_Backend.api_server.domain.recommendation.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecommendationExceptionMessage {

    RECOMMEND_REQUEST_FAILED("지역 추천 API 요청이 실패하였습니다.");

    private final String value;

}