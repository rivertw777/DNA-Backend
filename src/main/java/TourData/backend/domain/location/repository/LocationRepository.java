package TourData.backend.domain.location.repository;

import TourData.backend.domain.location.model.Location;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAll();

}