package TourData.backend.domain.location.service;

import static TourData.backend.domain.location.exception.LocationExceptionMessage.LOCATION_NOT_FOUND;

import TourData.backend.domain.location.dto.LocationDto.LocationResponse;
import TourData.backend.domain.location.dto.WeatherDto.WeatherResponse;
import TourData.backend.domain.location.exception.LocationException;
import TourData.backend.domain.location.model.entity.Location;
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

    // 전체 지역 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "Location", cacheManager = "redisCacheManager")
    public List<LocationResponse> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(this::toResponseDto) // 메서드 참조를 사용하여 가독성 향상
                .collect(Collectors.toList());
    }

    // 전체 지역 날씨 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "LocationWeather", cacheManager = "redisCacheManager")
    public List<WeatherResponse> getLocationWeatherInfo() {
        List<Location> locations = locationRepository.findAll();
        return weatherService.getWeatherResponses(locations);
    }

    private LocationResponse toResponseDto(Location location) {
        return new LocationResponse(
                location.getId(),
                location.getName(),
                location.getThumbnail()
        );
    }

}


