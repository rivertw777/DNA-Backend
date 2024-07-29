package TourData.backend.domain.location.service;

import static TourData.backend.domain.location.exception.LocationExceptionMessage.ALREADY_LIKE;
import static TourData.backend.domain.location.exception.LocationExceptionMessage.ALREADY_UNLIKE;

import TourData.backend.domain.location.dto.LocationDto.LocationLikeCheckResponse;
import TourData.backend.domain.location.dto.LocationDto.LocationLikeCountResponse;
import TourData.backend.domain.location.exception.LocationException;
import TourData.backend.domain.location.model.Location;
import TourData.backend.domain.location.model.LocationLike;
import TourData.backend.domain.location.repository.LocationLikeRepository;
import TourData.backend.domain.user.model.User;
import TourData.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationLikeService {

    private final LocationLikeRepository locationLikeRepository;
    private final UserService userService;
    private final LocationService locationService;
    private final LocationLikeCountService locationLikeCountService;

    // 지역 좋아요
    @Transactional
    public void likeLocation(String username, Long locationId) {
        User user = userService.findUser(username);
        Location location = locationService.findLocation(locationId);
        ensureNotLiked(user, location);
        saveLocation(user, location);
        locationLikeCountService.increaseCount(locationId);
    }

    private void ensureNotLiked(User user, Location location) {
        if (locationLikeRepository.findByLocationAndUser(location, user).isPresent()) {
            throw new LocationException(ALREADY_LIKE.getMessage());
        }
    }

    private void saveLocation(User user, Location location) {
        LocationLike locationLike = LocationLike.builder()
                .location(location)
                .user(user)
                .build();
        locationLikeRepository.save(locationLike);
    }

    // 지역 좋아요 취소
    @Transactional
    public void unlikeLocation(String username, Long locationId) {
        User user = userService.findUser(username);
        Location location = locationService.findLocation(locationId);
        ensureLiked(user, location);
        locationLikeRepository.deleteByLocationAndUser(location, user);
        locationLikeCountService.decreaseCount(locationId);
    }

    private void ensureLiked(User user, Location location) {
        if (locationLikeRepository.findByLocationAndUser(location, user).isEmpty()) {
            throw new LocationException(ALREADY_UNLIKE.getMessage());
        }
    }

    // 지역 좋아요 여부 확인
    @Transactional(readOnly = true)
    public LocationLikeCheckResponse checkLocationLike(String username, Long locationId) {
        User user = userService.findUser(username);
        Location location = locationService.findLocation(locationId);
        boolean isLike = locationLikeRepository.findByLocationAndUser(location, user).isPresent();
        return new LocationLikeCheckResponse(isLike);
    }

    // 지역 좋아요 수 조회
    @Transactional(readOnly = true)
    public LocationLikeCountResponse getLocationLikeCount(Long locationId) {
        Integer likeCount = locationLikeCountService.getCount(locationId);
        return new LocationLikeCountResponse(likeCount);
    }

}
