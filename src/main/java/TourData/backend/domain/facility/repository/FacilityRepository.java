package TourData.backend.domain.facility.repository;

import TourData.backend.domain.facility.model.entity.Facility;
import TourData.backend.domain.facility.model.enums.FacilityType;
import TourData.backend.domain.location.model.enums.LocationName;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findByLatitudeBetweenAndLongitudeBetweenAndType(
            double latMin, double latMax, double lngMin, double lngMax, FacilityType type);
    List<Facility> findByLocationNameAndType(LocationName locationName, FacilityType type);
    List<Facility> findByLocationName(LocationName locationName);
}