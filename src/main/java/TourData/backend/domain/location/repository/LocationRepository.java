package TourData.backend.domain.location.repository;

import TourData.backend.domain.location.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}