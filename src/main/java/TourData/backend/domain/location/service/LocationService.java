package TourData.backend.domain.location.service;

import TourData.backend.domain.location.dto.LocationDto.LocationResponse;
import java.util.List;

public interface LocationService {
    List<LocationResponse> getAllLocations();

}
