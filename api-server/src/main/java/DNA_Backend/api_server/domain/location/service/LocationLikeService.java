package DNA_Backend.api_server.domain.location.service;

import static DNA_Backend.api_server.domain.location.exception.LocationExceptionMessage.ALREADY_LIKE;
import static DNA_Backend.api_server.domain.location.exception.LocationExceptionMessage.ALREADY_UNLIKE;

import DNA_Backend.api_server.domain.location.dto.LocationDto.CheckLocationLikeResponse;
import DNA_Backend.api_server.domain.location.dto.LocationDto.LocationLikeCountResponse;
import DNA_Backend.api_server.domain.location.exception.LocationException;
import DNA_Backend.api_server.domain.location.model.Location;
import DNA_Backend.api_server.domain.location.model.LocationLike;
import DNA_Backend.api_server.domain.location.repository.LocationLikeRepository;
import DNA_Backend.api_server.domain.user.model.User;
import DNA_Backend.api_server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationLikeService {

    private final LocationLikeRepository locationLikeRepository;
    private final LocationService locationService;
    private final LocationLikeCountService locationLikeCountService;
    private final UserService userService;

    // 사용자 지역 좋아요
    @Transactional
    public void likeLocation(Long userId, Long locationId) {
        User user = userService.findUser(userId);
        Location location = locationService.findLocation(locationId);
        validateLikeNotExists(user.getId(), locationId);
        saveLocationLike(user, location);
        locationLikeCountService.increaseCount(locationId);
    }

    private void validateLikeNotExists(Long userId, Long locationId) {
        if (locationLikeRepository.findByUserIdAndLocationId(userId, locationId).isPresent()) {
            throw new LocationException(ALREADY_LIKE.getMessage());
        }
    }

    private void saveLocationLike(User user, Location location) {
        LocationLike locationLike = LocationLike.createLocationLike(user, location);
        locationLikeRepository.save(locationLike);
    }

    // 사용자 지역 좋아요 취소
    @Transactional
    public void unlikeLocation(Long userId, Long locationId) {
        validateLikeExists(userId, locationId);
        locationLikeRepository.deleteByUserIdAndLocationId(userId, locationId);
        locationLikeCountService.decreaseCount(locationId);
    }

    private void validateLikeExists(Long userId, Long locationId) {
        if (locationLikeRepository.findByUserIdAndLocationId(userId, locationId).isEmpty()) {
            throw new LocationException(ALREADY_UNLIKE.getMessage());
        }
    }

    // 사용자 지역 좋아요 여부 확인
    @Transactional(readOnly = true)
    public CheckLocationLikeResponse checkLocationLike(Long userId, Long locationId) {
        boolean isLiked = locationLikeRepository.findByUserIdAndLocationId(userId, locationId).isPresent();
        return new CheckLocationLikeResponse(isLiked);
    }

    // 단일 지역 좋아요 수 조회
    @Transactional(readOnly = true)
    public LocationLikeCountResponse getLocationLikeCount(Long locationId) {
        long likeCount = locationLikeCountService.getCount(locationId);
        return new LocationLikeCountResponse(likeCount);
    }

}