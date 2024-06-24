package TourData.backend.domain.location.repository;

import TourData.backend.domain.location.model.Location;
import TourData.backend.domain.location.model.LocationLike;
import TourData.backend.domain.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationLikeRepository extends JpaRepository<LocationLike, Long> {
    Optional<LocationLike> findByLocationAndUser(Location location, User user);
    void deleteByLocationAndUser(Location location, User user);
}