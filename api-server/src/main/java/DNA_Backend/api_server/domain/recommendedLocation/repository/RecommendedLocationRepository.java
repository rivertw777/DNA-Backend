package DNA_Backend.api_server.domain.recommendedLocation.repository;

import DNA_Backend.api_server.domain.recommendedLocation.model.entity.RecommendedLocation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecommendedLocationRepository extends JpaRepository<RecommendedLocation, Long> {
    @Query("SELECT rl FROM RecommendedLocation rl " +
            "JOIN FETCH rl.location " +
            "WHERE rl.user.id = :userId " +
            "ORDER BY rl.ranking ASC")
    List<RecommendedLocation> findByUserIdOrderByRankingAscWithFetchJoin(Long userId);

    void deleteByUserId(Long userId);
}