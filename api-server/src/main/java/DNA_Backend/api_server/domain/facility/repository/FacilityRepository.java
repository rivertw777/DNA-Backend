package DNA_Backend.api_server.domain.facility.repository;

import DNA_Backend.api_server.domain.facility.model.Facility;
import DNA_Backend.api_server.domain.facility.model.FacilityType;
import DNA_Backend.api_server.domain.location.model.Location;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findByLatitudeBetweenAndLongitudeBetweenAndType(
            double latMin, double latMax, double lngMin, double lngMax, FacilityType type);
    List<Facility> findByLocationIdAndType(Long locationId, FacilityType type);
    long countByLocation(Location location);
}