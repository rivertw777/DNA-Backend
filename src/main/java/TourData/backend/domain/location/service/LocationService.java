package TourData.backend.domain.location.service;

import TourData.backend.domain.location.dto.LocationDto.LocationLikeCheckResponse;
import TourData.backend.domain.location.dto.LocationDto.LocationLikeCountResponse;
import TourData.backend.domain.location.dto.LocationDto.LocationResponse;
import java.util.List;

public interface LocationService {
    List<LocationResponse> getAllLocations();
    void likeLocation(String username, Long locationId);
    void unlikeLocation(String username, Long locationId);
    LocationLikeCheckResponse checkLocationLike(String username, Long locationId);
    LocationLikeCountResponse getLocationLikeCount(Long locationId);
}
