package DNA_Backend.api_server.domain.facility.service;

import static DNA_Backend.api_server.domain.facility.exception.FacilityExceptionMessage.FACILITY_NOT_FOUND;

import DNA_Backend.api_server.domain.facility.dto.mapper.FacilityMapper;
import DNA_Backend.api_server.domain.facility.dto.response.FacilityResponse;
import DNA_Backend.api_server.domain.facility.dto.response.LocationTotalFacilityCountResponse;
import DNA_Backend.api_server.domain.facility.model.entity.Facility;
import DNA_Backend.api_server.domain.facility.model.enums.FacilityType;
import DNA_Backend.api_server.domain.facility.repository.FacilityRepository;
import DNA_Backend.api_server.common.exception.DnaApplicationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final FacilityMapper facilityMapper;

    // id로 조회
    public Facility findFacility(Long facilityId) {
        return facilityRepository.findById(facilityId)
                .orElseThrow(()->new DnaApplicationException(FACILITY_NOT_FOUND.getValue()));
    }

    // PUBLIC - 시설 검색 by 지역 Id & 타입
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "FacilitySearchResults", key = "#p0 + ':' + #p1", cacheManager = "redisCacheManager")
    public List<FacilityResponse> searchFacilitiesByLocationIdAndType(Long locationId, String facilityType) {
        FacilityType type = FacilityType.fromValue(facilityType);
        List<Facility> facilities = facilityRepository.findByLocationIdAndType(locationId, type);
        return facilityMapper.toResponses(facilities);
    }

    // PUBLIC - 전체 지역 총 시설 수 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "AllLocationTotalFacilityCounts", cacheManager = "redisCacheManager")
    public List<LocationTotalFacilityCountResponse> getAllLocationTotalFacilityCounts() {
        return facilityRepository.countTotalFacilitiesGroupedByLocation();
    }

    // PUBLIC - 단일 지역 총 시설 수 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "LocationTotalFacilityCount", key= "#p0", cacheManager = "redisCacheManager")
    public LocationTotalFacilityCountResponse getLocationTotalFacilityCount(Long locationId) {
        return toLocationTotalFacilityCountResponseDto(locationId);
    }

    private LocationTotalFacilityCountResponse toLocationTotalFacilityCountResponseDto(Long locationId) {
        long facilityCount = facilityRepository.countByLocationId(locationId);
        return new LocationTotalFacilityCountResponse(locationId, facilityCount);
    }

}
