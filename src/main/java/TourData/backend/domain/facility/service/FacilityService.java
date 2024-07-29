package TourData.backend.domain.facility.service;

import static TourData.backend.domain.facility.exception.FacilityExceptionMessage.FACILITY_NOT_FOUND;

import TourData.backend.domain.facility.dto.FacilityDto.FacilitySearchResponse;
import TourData.backend.domain.facility.exception.FacilityException;
import TourData.backend.domain.facility.model.Facility;
import TourData.backend.domain.facility.model.FacilityType;
import TourData.backend.domain.facility.repository.FacilityRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    // id로 조회
    @Transactional(readOnly = true)
    public Facility findFacility(Long facilityId) {
        return facilityRepository.findById(facilityId)
                .orElseThrow(()->new FacilityException(FACILITY_NOT_FOUND.getMessage()));
    }

    // 시설 검색
    @Transactional(readOnly = true)
    public List<FacilitySearchResponse> searchFacilities(double latMin, double latMax, double lngMin, double lngMax, String type) {
        FacilityType facilityType = FacilityType.fromValue(type);
        List<Facility> facilities = facilityRepository.findByLatitudeBetweenAndLongitudeBetweenAndType(
                latMin, latMax, lngMin, lngMax, facilityType);

        return facilities.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private FacilitySearchResponse convertToResponse(Facility facility) {
        return new FacilitySearchResponse(
                facility.getId(),
                facility.getName(),
                facility.getType().getValue(),
                facility.getAddress(),
                facility.getLatitude(),
                facility.getLongitude());
    }

}
