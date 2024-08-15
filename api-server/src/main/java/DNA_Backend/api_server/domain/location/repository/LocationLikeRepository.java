package DNA_Backend.api_server.domain.location.repository;

import DNA_Backend.api_server.domain.location.model.LocationLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationLikeRepository extends JpaRepository<LocationLike, Long> {
    Optional<LocationLike> findByUserIdAndLocationId(Long userId, Long locationId);
    void deleteByUserIdAndLocationId(Long userId, Long locationId);
}