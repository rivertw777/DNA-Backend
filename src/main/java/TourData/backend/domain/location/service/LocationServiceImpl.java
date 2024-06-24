package TourData.backend.domain.location.service;

import static TourData.backend.domain.location.exception.LocationExceptionMessage.LOCATION_NOT_FOUND;

import TourData.backend.domain.location.dto.LocationDto.LocationLikeCheckResponse;
import TourData.backend.domain.location.dto.LocationDto.LocationResponse;
import TourData.backend.domain.location.exception.LocationException;
import TourData.backend.domain.location.model.Location;
import TourData.backend.domain.location.model.LocationLike;
import TourData.backend.domain.location.repository.LocationLikeRepository;
import TourData.backend.domain.location.repository.LocationRepository;
import TourData.backend.domain.user.model.User;
import TourData.backend.domain.user.service.UserSerivce;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationLikeRepository locationLikeRepository;

    private final UserSerivce userSerivce;

    @Override
    @Transactional(readOnly = true)
    public List<LocationResponse> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(location -> new LocationResponse(location.getName(), location.getThumbNail()))
                .collect(Collectors.toList());
    }

    private Location findLocation(Long locationId){
        return locationRepository.findById(locationId)
                .orElseThrow(()->new LocationException(LOCATION_NOT_FOUND.getMessage()));
    }

    @Override
    @Transactional
    public void likeLocation(String username, Long locationId) {
        User user = userSerivce.findUser(username);
        Location location = findLocation(locationId);
        LocationLike locationLike = LocationLike.builder()
                .location(location)
                .user(user)
                .build();
        locationLikeRepository.save(locationLike);
    }

    @Override
    @Transactional
    public void unlikeLocation(String username, Long locationId) {
        User user = userSerivce.findUser(username);
        Location location = findLocation(locationId);
        locationLikeRepository.deleteByLocationAndUser(location, user);
    }

    @Override
    @Transactional(readOnly = true)
    public LocationLikeCheckResponse checkLocationLike(String username, Long locationId) {
        User user = userSerivce.findUser(username);
        Location location = findLocation(locationId);
        boolean isLike = locationLikeRepository.findByLocationAndUser(location, user).isPresent();
        return new LocationLikeCheckResponse(isLike);
    }

}
