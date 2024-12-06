package DNA_Backend.api_server.domain.recommendedLocation.service;

import static DNA_Backend.api_server.domain.recommendedLocation.exception.RecommendedLocationExceptionMessage.LOCATION_RECOMMEND_REQUEST_FAILED;

import DNA_Backend.api_server.domain.location.model.entity.Location;
import DNA_Backend.api_server.domain.location.model.enums.LocationName;
import DNA_Backend.api_server.domain.location.service.LocationService;
import DNA_Backend.api_server.domain.recommendedLocation.dto.mapper.RecommendedLocationMapper;
import DNA_Backend.api_server.domain.recommendedLocation.dto.request.RecommendLocationRequest;
import DNA_Backend.api_server.domain.recommendedLocation.dto.LocationData;
import DNA_Backend.api_server.domain.recommendedLocation.dto.response.RecommendLocationResponse;
import DNA_Backend.api_server.domain.recommendedLocation.dto.response.RecommendedLocationResponse;
import DNA_Backend.api_server.domain.recommendedLocation.model.entity.RecommendedLocation;
import DNA_Backend.api_server.domain.recommendedLocation.repository.RecommendedLocationRepository;
import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.user.service.UserService;
import DNA_Backend.api_server.common.exception.DnaApplicationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RecommendedLocationService {

    @Value("${inference.app.url}")
    private String inferenceAppUrl;

    private final RecommendedLocationRepository recommendedLocationRepository;
    private final UserService userService;
    private final LocationService locationService;
    private final RecommendedLocationMapper recommendedLocationMapper;
    private final RestTemplate restTemplate;

    // USER - 지역 추천
    @Transactional
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public RecommendLocationResponse recommendLocation(Long userId, RecommendLocationRequest requestParam) {
        User user = userService.findUser(userId);
        RecommendLocationResponse response = getRecommendLocationResponse(requestParam);
        initUserRecommendedLocations(user, response.locations());
        return response;
    }

    // 추천 API 요청
    private RecommendLocationResponse getRecommendLocationResponse(RecommendLocationRequest requestParam) {
        return restTemplate.postForObject(
                inferenceAppUrl + "/recommend",
                requestParam,
                RecommendLocationResponse.class);
    }

    @Recover
    public RecommendLocationResponse recover(Exception e) {
        throw new DnaApplicationException(LOCATION_RECOMMEND_REQUEST_FAILED.getValue());
    }

    // 사용자 추천 지역 초기화
    private void initUserRecommendedLocations(User user, List<LocationData> locations) {
        recommendedLocationRepository.deleteByUserId(user.getId());

        for (LocationData locationData : locations) {
            LocationName locationName = LocationName.fromValue(locationData.locationName());
            Location location = locationService.findLocation(locationName);
            saveRecommendedLocation(user, location, locationData.ranking());
        }
    }

    private void saveRecommendedLocation(User user, Location location, int ranking) {
        RecommendedLocation recommendedLocation = RecommendedLocation.createRecommendedLocation(user, location, ranking);
        recommendedLocationRepository.save(recommendedLocation);
    }

    // USER - 전체 추천 지역 조회
    @Transactional(readOnly = true)
    public List<RecommendedLocationResponse> getRecommendedLocations(Long userId) {
        List<RecommendedLocation> recommendedLocations = recommendedLocationRepository.findByUserIdOrderByRankingAscWithFetchJoin(userId);
        return recommendedLocationMapper.toResponses(recommendedLocations);
    }

}
