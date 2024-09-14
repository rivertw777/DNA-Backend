package DNA_Backend.api_server.domain.recommendedLocation.service;

import static DNA_Backend.api_server.domain.recommendedLocation.message.RecommendedLocationExceptionMessage.LOCATION_RECOMMEND_REQUEST_FAILED;

import DNA_Backend.api_server.domain.location.model.entity.Location;
import DNA_Backend.api_server.domain.location.model.enums.LocationName;
import DNA_Backend.api_server.domain.location.service.LocationService;
import DNA_Backend.api_server.domain.recommendedLocation.dto.mapper.RecommendedLocationMapper;
import DNA_Backend.api_server.domain.recommendedLocation.dto.request.RecommendLocationRequest;
import DNA_Backend.api_server.domain.recommendedLocation.dto.response.RecommendLocationResponse;
import DNA_Backend.api_server.domain.recommendedLocation.dto.response.RecommendedLocationResponse;
import DNA_Backend.api_server.domain.recommendedLocation.model.entity.RecommendedLocation;
import DNA_Backend.api_server.domain.recommendedLocation.repository.RecommendedLocationRepository;
import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.user.service.UserService;
import DNA_Backend.api_server.global.exception.DnaApplicationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RecommendedLocationService {

    @Value("${inference.app.url}")
    private String inferenceAppUrl;

    private final RestTemplate restTemplate;
    private final UserService userService;
    private final LocationService locationService;
    private final RecommendedLocationRepository recommendedLocationRepository;
    private final RecommendedLocationMapper recommendedLocationMapper;

    // USER - 지역 추천
    @Transactional
    public RecommendLocationResponse recommendLocation(Long userId, RecommendLocationRequest requestParam) {
        User user = userService.findUser(userId);
        RecommendLocationResponse response = getRecommendLocationResponse(requestParam);
        initUserRecommendedLocations(user, response.locationNames());
        return response;
    }

    // 추천 API 요청
    private RecommendLocationResponse getRecommendLocationResponse(RecommendLocationRequest requestParam) {
        try {
            RecommendLocationResponse response = restTemplate.postForObject(
                    inferenceAppUrl + "/recommend",
                    requestParam,
                    RecommendLocationResponse.class
            );
            return response;
        } catch (Exception e) {
            throw new DnaApplicationException(LOCATION_RECOMMEND_REQUEST_FAILED.getValue());
        }
    }

    // 사용자 추천 지역 초기화
    private void initUserRecommendedLocations(User user, List<String> locationNames) {
        recommendedLocationRepository.deleteByUserId(user.getId());
        locationNames.stream()
                .map(LocationName::fromValue)
                .map(locationService::findLocation)
                .forEach(location -> saveRecommendedLocation(user, location));
    }

    private void saveRecommendedLocation(User user, Location location) {
        RecommendedLocation recommendedLocation = RecommendedLocation.createRecommendedLocation(user, location);
        recommendedLocationRepository.save(recommendedLocation);
    }

    // USER - 전체 추천 지역 조회
    @Transactional(readOnly = true)
    public List<RecommendedLocationResponse> getRecommendedLocations(Long userId) {
        List<RecommendedLocation> recommendedLocations = recommendedLocationRepository.findByUserId(userId);
        return recommendedLocationMapper.toResponses(recommendedLocations);
    }

}