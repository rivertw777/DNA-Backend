package DNA_Backend.api_server.domain.facility.repository;

import DNA_Backend.api_server.domain.facility.model.entity.FacilityBookmark;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacilityBookmarkRepository extends JpaRepository<FacilityBookmark, Long> {
    Optional<FacilityBookmark> findByUserIdAndFacilityId(Long userId, Long facilityId);

    void deleteByUserIdAndFacilityId(Long userId, Long facilityId);

    @Query("SELECT fb FROM FacilityBookmark fb " +
            "JOIN FETCH fb.facility f " +
            "JOIN FETCH f.location " +
            "WHERE fb.user.id = :userId " +
            "ORDER BY fb.createdAt DESC")
    List<FacilityBookmark> findByUserIdWithFetchJoin(Long userId);
}
