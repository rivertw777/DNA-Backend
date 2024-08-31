package DNA_Backend.api_server.domain.recommendation.service;

import static DNA_Backend.api_server.domain.recommendation.message.RecommendationExceptionMessage.RECOMMEND_REQUEST_FAILED;

import DNA_Backend.api_server.domain.location.model.entity.Location;
import DNA_Backend.api_server.domain.location.model.enums.LocationName;
import DNA_Backend.api_server.domain.location.service.LocationService;
import DNA_Backend.api_server.domain.recommendation.dto.RecommendationDto.RecommendLocationRequest;
import DNA_Backend.api_server.domain.recommendation.dto.RecommendationDto.RecommendLocationResponse;
import DNA_Backend.api_server.domain.recommendation.dto.RecommendationDto.RecommendedLocationResponse;
import DNA_Backend.api_server.domain.recommendation.model.entity.RecommendedLocation;
import DNA_Backend.api_server.domain.recommendation.repository.RecommendedLocationRepository;
import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.user.service.UserService;
import DNA_Backend.api_server.global.exception.DnaApplicationException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    @Value("${inference.app.url}")
    private String inferenceAppUrl;

    private final RestTemplate restTemplate;
    private final UserService userService;
    private final LocationService locationService;
    private final RecommendedLocationRepository recommendedLocationRepository;

    // 사용자 지역 추천
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
            throw new DnaApplicationException(RECOMMEND_REQUEST_FAILED.getMessage());
        }
    }

    // 사용자 추천 지역 초기화
    private void initUserRecommendedLocations(User user, List<String> locationNames) {
        List<RecommendedLocation> locations = user.getRecommendedLocations();

        locations.forEach(recommendedLocationRepository::delete);

        locationNames.stream()
                .map(LocationName::fromValue)
                .map(locationService::findLocation)
                .forEach(location -> saveRecommendedLocation(user, location));
    }

    // 추천 지역 저장
    private void saveRecommendedLocation(User user, Location location) {
        RecommendedLocation recommendedLocation = RecommendedLocation.createRecommendedLocation(user, location);
        recommendedLocationRepository.save(recommendedLocation);
    }

    // 사용자 추천 지역 조회
    @Transactional(readOnly = true)
    public List<RecommendedLocationResponse> getRecommendedLocations(Long userId) {
        return recommendedLocationRepository.findByUserId(userId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
    
    private RecommendedLocationResponse toResponseDto(RecommendedLocation recommendedLocation) {
        Location location = recommendedLocation.getLocation();
        return new RecommendedLocationResponse(
                location.getId(),
                location.getName().getValue(),
                location.getThumbnail()
        );
    }

}
