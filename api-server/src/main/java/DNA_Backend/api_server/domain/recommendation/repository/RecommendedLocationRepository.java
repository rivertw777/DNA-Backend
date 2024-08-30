package DNA_Backend.api_server.domain.recommendation.repository;

import DNA_Backend.api_server.domain.recommendation.model.entity.RecommendedLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedLocationRepository extends JpaRepository<RecommendedLocation, Long> {
}