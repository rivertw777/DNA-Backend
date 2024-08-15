package DNA_Backend.api_server.domain.location.repository;

import DNA_Backend.api_server.domain.location.model.Location;
import DNA_Backend.api_server.domain.location.model.LocationName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByName(LocationName name);
}