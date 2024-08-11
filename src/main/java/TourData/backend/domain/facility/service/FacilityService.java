package TourData.backend.domain.facility.service;

import static TourData.backend.domain.facility.exception.FacilityExceptionMessage.FACILITY_NOT_FOUND;

import TourData.backend.domain.facility.dto.FacilityDto.FacilityResponse;
import TourData.backend.domain.facility.dto.FacilityDto.LocationTotalFacilityCountResponse;
import TourData.backend.domain.facility.exception.FacilityException;
import TourData.backend.domain.facility.model.Facility;
import TourData.backend.domain.facility.model.FacilityType;
import TourData.backend.domain.facility.repository.FacilityRepository;
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
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final LocationRepository locationRepository;

    // id로 조회
    @Transactional(readOnly = true)
    public Facility findFacility(Long facilityId) {
        return facilityRepository.findById(facilityId)
                .orElseThrow(()->new FacilityException(FACILITY_NOT_FOUND.getMessage()));
    }

    // 시설 검색 by 위도, 경도 & 타입
    @Transactional(readOnly = true)
    public List<FacilityResponse> searchFacilities(double latMin, double latMax, double lngMin, double lngMax, String facilityType) {
        FacilityType type = FacilityType.fromValue(facilityType);
        List<Facility> facilities = facilityRepository.findByLatitudeBetweenAndLongitudeBetweenAndType(
                latMin, latMax, lngMin, lngMax, type);

        return facilities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    // 시설 검색 by 지역 id & 타입
    @Transactional(readOnly = true)
    public List<FacilityResponse> searchFacilitiesByLocationIdAndType(Long locationId, String facilityType) {
        FacilityType type = FacilityType.fromValue(facilityType);
        List<Facility> facilities = facilityRepository.findByLocationIdAndType(locationId, type);

        return facilities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private FacilityResponse toResponseDto(Facility facility) {
        return new FacilityResponse(
                facility.getId(),
                facility.getName(),
                facility.getType().getValue(),
                facility.getAddress(),
                facility.getLatitude(),
                facility.getLongitude());
    }

    // 전체 지역 총 시설 수 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "TotalFacilityCountsForAllLocations", cacheManager = "redisCacheManager")
    public List<LocationTotalFacilityCountResponse> getTotalFacilityCountsForAllLocations() {
        List<Location> locations = locationRepository.findAll();

        return locations.stream()
                .map(this::toTotalFacilityCountResponseDto)
                .collect(Collectors.toList());
    }

    private LocationTotalFacilityCountResponse toTotalFacilityCountResponseDto(Location location) {
        long facilityCount = facilityRepository.countByLocation(location);
        return new LocationTotalFacilityCountResponse(
                location.getId(),
                location.getName().getValue(),
                facilityCount
        );
    }

}
