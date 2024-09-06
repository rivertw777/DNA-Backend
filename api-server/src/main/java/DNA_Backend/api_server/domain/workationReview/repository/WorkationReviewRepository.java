package DNA_Backend.api_server.domain.workationReview.repository;

import DNA_Backend.api_server.domain.workationReview.model.entity.WorkationReview;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkationReviewRepository extends JpaRepository<WorkationReview, Long> {
    List<WorkationReview> findByUserId(Long userId);
    Page<WorkationReview> findByWorkationScheduleLocationId(Long locationId, Pageable pageable);
    List<WorkationReview> findByWorkationScheduleLocationId(Long locationId);
}