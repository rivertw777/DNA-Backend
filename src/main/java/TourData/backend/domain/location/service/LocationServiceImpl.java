package TourData.backend.domain.location.service;


import TourData.backend.domain.location.dto.LocationDto.LocationResponse;
import TourData.backend.domain.location.repository.LocationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public List<LocationResponse> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(location -> new LocationResponse(location.getName(), location.getThumbNail()))
                .collect(Collectors.toList());
    }

}
