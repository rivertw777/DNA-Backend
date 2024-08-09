package TourData.backend.domain.location.service;

import static TourData.backend.domain.location.exception.LocationExceptionMessage.ALREADY_LIKE;
import static TourData.backend.domain.location.exception.LocationExceptionMessage.ALREADY_UNLIKE;

import TourData.backend.domain.location.dto.LocationDto.LocationLikeCheckResponse;
import TourData.backend.domain.location.dto.LocationDto.LocationLikeCountResponse;
import TourData.backend.domain.location.exception.LocationException;
import TourData.backend.domain.location.model.entity.Location;
import TourData.backend.domain.location.model.entity.LocationLike;
import TourData.backend.domain.location.repository.LocationLikeRepository;
import TourData.backend.domain.user.model.entity.User;
import TourData.backend.domain.user.service.UserService;
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

    // 지역 좋아요
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

    // 지역 좋아요 취소
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

    // 지역 좋아요 여부 확인
    @Transactional(readOnly = true)
    public LocationLikeCheckResponse checkLocationLike(Long userId, Long locationId) {
        boolean isLike = locationLikeRepository.findByUserIdAndLocationId(userId, locationId).isPresent();
        return new LocationLikeCheckResponse(isLike);
    }

    // 단일 지역 좋아요 수 조회
    public LocationLikeCountResponse getLocationLikesCount(Long locationId) {
        int likesCount = locationLikeCountService.getCount(locationId);
        return new LocationLikeCountResponse(likesCount);
    }

}
