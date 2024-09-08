package DNA_Backend.api_server.domain.location.repository;

import DNA_Backend.api_server.domain.location.model.entity.Location;
import DNA_Backend.api_server.domain.location.model.enums.LocationName;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAllByOrderByIdAsc();
    Optional<Location> findByName(LocationName locationName);
}