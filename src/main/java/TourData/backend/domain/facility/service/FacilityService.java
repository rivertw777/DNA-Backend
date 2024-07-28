package TourData.backend.domain.facility.service;

import TourData.backend.domain.facility.dto.FacilityDto.FacilitySearchResponse;
import TourData.backend.domain.facility.model.Facility;
import TourData.backend.domain.facility.model.FacilityType;
import TourData.backend.domain.facility.repository.FacilityRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public List<FacilitySearchResponse> searchFacilities(double latMin, double latMax, double lngMin, double lngMax, String type) {
        FacilityType facilityType = FacilityType.fromValue(type);
        List<Facility> facilities = facilityRepository.findByLatitudeBetweenAndLongitudeBetweenAndType(
                latMin, latMax, lngMin, lngMax, facilityType);

        return facilities.stream()
                .map(facility -> new FacilitySearchResponse(
                        facility.getName(),
                        facility.getType().getValue(),
                        facility.getAddress(),
                        facility.getLatitude(),
                        facility.getLongitude()))
                .collect(Collectors.toList());
    }

}
