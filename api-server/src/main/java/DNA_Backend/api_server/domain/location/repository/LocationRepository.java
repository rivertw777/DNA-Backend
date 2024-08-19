package DNA_Backend.api_server.domain.location.repository;

import DNA_Backend.api_server.domain.location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}