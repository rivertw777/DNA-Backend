package DNA_Backend.api_server.domain.location.service;

import static DNA_Backend.api_server.domain.location.exception.LocationExceptionMessage.LOCATION_NOT_FOUND;

import DNA_Backend.api_server.domain.facility.service.FacilityService;
import DNA_Backend.api_server.domain.location.dto.LocationDto.LocationDetailResponse;
import DNA_Backend.api_server.domain.location.dto.LocationDto.LocationResponse;
import DNA_Backend.api_server.domain.location.dto.LocationWeatherDto.LocationWeatherResponse;
import DNA_Backend.api_server.domain.location.exception.LocationException;
import DNA_Backend.api_server.domain.location.model.Location;
import DNA_Backend.api_server.domain.location.repository.LocationRepository;
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
    private final LocationWeatherService locationWeatherService;
    private final FacilityService facilityService;

    // id로 조회
    @Transactional(readOnly = true)
    public Location findLocation(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationException(LOCATION_NOT_FOUND.getMessage()));
    }

    // 전체 지역 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "AllLocations", cacheManager = "redisCacheManager")
    public List<LocationResponse> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private LocationResponse toResponseDto(Location location) {
        return new LocationResponse(
                location.getId(),
                location.getName().getValue(),
                location.getThumbnail(),
                location.getLatitude(),
                location.getLongitude()
        );
    }

    // 단일 지역 상세 조회
    public LocationDetailResponse getLocationDetail(Long locationId) {
        Location location = findLocation(locationId);

        return toDetailResponseDto(location);
    }

    private LocationDetailResponse toDetailResponseDto(Location location) {
        return new LocationDetailResponse(
                location.getId(),
                location.getName().getValue(),
                location.getThumbnail(),
                location.getLatitude(),
                location.getLongitude(),
                location.getInternetSpeed(),
                location.getPriceIndex(),
                location.getPopulationDensity()
        );
    }

    // 전체 지역 날씨 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "AllLocationWeathers", cacheManager = "redisCacheManager")
    public List<LocationWeatherResponse> getAllLocationWeathers() {
        List<Location> locations = locationRepository.findAll();

        return locationWeatherService.toWeatherResponseDtos(locations);
    }

    // 단일 지역 날씨 조회
    @Transactional(readOnly = true)
    public LocationWeatherResponse getLocationWeather(Long locationId) {
        Location location = findLocation(locationId);

        return locationWeatherService.toWeatherResponseDto(location);
    }

}


