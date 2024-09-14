package DNA_Backend.api_server.domain.recommendedLocation.repository;

import DNA_Backend.api_server.domain.recommendedLocation.model.entity.RecommendedLocation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecommendedLocationRepository extends JpaRepository<RecommendedLocation, Long> {
    @Query("SELECT rl FROM RecommendedLocation rl "
            + "JOIN FETCH rl.location "
            + "WHERE rl.user.id = :userId")
    List<RecommendedLocation> findByUserId(@Param("userId") Long userId);
    void deleteByUserId(Long userId);
}