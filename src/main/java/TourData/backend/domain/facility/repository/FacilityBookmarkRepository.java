package TourData.backend.domain.facility.repository;

import TourData.backend.domain.facility.model.Facility;
import TourData.backend.domain.facility.model.FacilityBookmark;
import TourData.backend.domain.user.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityBookmarkRepository extends JpaRepository<FacilityBookmark, Long> {
    Optional<FacilityBookmark> findByUserAndFacility(User user, Facility facility);
    void deleteByUserAndFacility(User user, Facility facility);
    List<FacilityBookmark> findByUser(User user);
}
