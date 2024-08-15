package TourData.backend.domain.location.repository;

import TourData.backend.domain.location.model.Location;
import TourData.backend.domain.location.model.LocationName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByName(LocationName name);
}