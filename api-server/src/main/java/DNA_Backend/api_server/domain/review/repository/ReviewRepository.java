package DNA_Backend.api_server.domain.review.repository;

import DNA_Backend.api_server.domain.review.model.entity.Review;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long userId);
    Page<Review> findByWorkationScheduleLocationId(Long locationId, Pageable pageable);
    List<Review> findByWorkationScheduleLocationId(Long locationId);
}