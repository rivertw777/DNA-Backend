package DNA_Backend.api_server.domain.recommendation.service;

import DNA_Backend.api_server.domain.recommendation.dto.RecommendationDto.RecommendLocationRequest;
import DNA_Backend.api_server.domain.recommendation.dto.RecommendationDto.RecommendLocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    @Value("${inference.app.url}")
    private String inferenceAppUrl;

    private final RestTemplate restTemplate;

    // 사용자 지역 추천
    public RecommendLocationResponse recommendLocation(Long userId, RecommendLocationRequest requestParam) {
        RecommendLocationResponse response = restTemplate.postForObject(
                inferenceAppUrl + "/recommend",
                requestParam,
                RecommendLocationResponse.class
        );
        return response;
    }

}
