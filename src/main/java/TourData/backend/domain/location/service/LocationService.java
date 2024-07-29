package TourData.backend.domain.location.service;

import static TourData.backend.domain.location.exception.LocationExceptionMessage.LOCATION_NOT_FOUND;

import TourData.backend.domain.location.dto.LocationDto.LocationResponse;
import TourData.backend.domain.location.dto.WeatherDto.WeatherResponse;
import TourData.backend.domain.location.exception.LocationException;
import TourData.backend.domain.location.model.Location;
import TourData.backend.domain.location.repository.LocationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final WeatherService weatherService;

    // id로 조회
    @Transactional(readOnly = true)
    public Location findLocation(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationException(LOCATION_NOT_FOUND.getMessage()));
    }

    // 지역 전체 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "Location")
    public List<LocationResponse> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(location -> new LocationResponse(location.getId(), location.getName(), location.getThumbnail()))
                .collect(Collectors.toList());
    }

    // 전체 지역 날씨 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "LocationWeather")
    public List<WeatherResponse> getLocationWeatherInfo() {
        List<Location> locations = locationRepository.findAll();
        return weatherService.getWeatherResponses(locations);
    }

}


