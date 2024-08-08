package TourData.backend.domain.location.repository;

import TourData.backend.domain.location.model.entity.LocationLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationLikeRepository extends JpaRepository<LocationLike, Long> {
    Optional<LocationLike> findByUserIdAndLocationId(Long userId, Long locationId);
    void deleteByUserIdAndLocationId(Long userId, Long locationId);
}