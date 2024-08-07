package TourData.backend.domain.facility.repository;

import TourData.backend.domain.facility.model.Facility;
import TourData.backend.domain.facility.model.FacilityType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findByLatitudeBetweenAndLongitudeBetweenAndType(
            double latMin, double latMax, double lngMin, double lngMax, FacilityType type);
    List<Facility> findByLocation_CodeAndType(String locationCode, FacilityType type);

}