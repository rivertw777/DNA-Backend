package DNA_Backend.api_server.domain.location.service;

import static DNA_Backend.api_server.domain.location.exception.LocationExceptionMessage.LOCATION_NOT_FOUND;

import DNA_Backend.api_server.domain.location.dto.mapper.LocationMapper;
import DNA_Backend.api_server.domain.location.dto.response.LocationWeatherResponse;
import DNA_Backend.api_server.domain.location.dto.response.LocationDetailResponse;
import DNA_Backend.api_server.domain.location.dto.response.LocationResponse;
import DNA_Backend.api_server.domain.location.model.entity.Location;
import DNA_Backend.api_server.domain.location.model.enums.LocationName;
import DNA_Backend.api_server.domain.location.repository.LocationRepository;
import DNA_Backend.api_server.common.exception.DnaApplicationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationWeatherService locationWeatherService;
    private final LocationMapper locationMapper;

    // id로 조회
    public Location findLocation(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new DnaApplicationException(LOCATION_NOT_FOUND.getValue()));
    }

    // 이름으로 조회
    public Location findLocation(LocationName locationName) {
        return locationRepository.findByName(locationName)
                .orElseThrow(() -> new DnaApplicationException(LOCATION_NOT_FOUND.getValue()));
    }

    // PUBLIC - 전체 지역 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "AllLocations", cacheManager = "redisCacheManager")
    public List<LocationResponse> getAllLocations() {
        List<Location> locations = locationRepository.findAllByOrderByIdAsc();
        return locationMapper.toResponses(locations);
    }

    // PUBLIC - 단일 지역 상세 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "LocationDetail", key= "#p0", cacheManager = "redisCacheManager")
    public LocationDetailResponse getLocationDetail(Long locationId) {
        Location location = findLocation(locationId);
        return locationMapper.toDetailResponse(location);
    }

    // PUBLIC - 전체 지역 날씨 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "AllLocationWeathers", cacheManager = "redisCacheManager")
    public List<LocationWeatherResponse> getAllLocationWeathers() {
        List<Location> locations = locationRepository.findAll();
        return locationWeatherService.getAllLocationWeathers(locations);
    }

    // PUBLIC - 단일 지역 날씨 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "LocationWeather", key= "#p0", cacheManager = "redisCacheManager")
    public LocationWeatherResponse getLocationWeather(Long locationId) {
        Location location = findLocation(locationId);
        return locationWeatherService.getLocationWeather(location);
    }

}


