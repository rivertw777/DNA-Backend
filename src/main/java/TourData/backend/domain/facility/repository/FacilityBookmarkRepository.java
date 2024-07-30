package TourData.backend.domain.facility.repository;

import TourData.backend.domain.facility.model.FacilityBookmark;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityBookmarkRepository extends JpaRepository<FacilityBookmark, Long> {
    Optional<FacilityBookmark> findByUserIdAndFacilityId(Long userId, Long facilityId);
    void deleteByUserIdAndFacilityId(Long userId, Long facilityId);
    List<FacilityBookmark> findByUserId(Long userId);
}
