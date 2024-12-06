package DNA_Backend.api_server.domain.workationReview.repository;

import DNA_Backend.api_server.domain.workationReview.model.entity.WorkationReview;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkationReviewRepository extends JpaRepository<WorkationReview, Long> {
    @Query("SELECT wr FROM WorkationReview wr " +
            "JOIN FETCH wr.workationSchedule ws " +
            "JOIN FETCH ws.user u " +
            "JOIN FETCH u.roles " +
            "JOIN FETCH ws.location")
    Page<WorkationReview> findAllWithFetchJoin(Pageable pageable);

    @Query("SELECT wr FROM WorkationReview wr " +
            "JOIN FETCH wr.workationSchedule ws " +
            "JOIN FETCH ws.user u " +
            "JOIN FETCH u.roles " +
            "JOIN FETCH ws.location " +
            "WHERE ws.location.id = :locationId")
    Page<WorkationReview> findByWorkationScheduleLocationIdWithFetchJoin(Long locationId, Pageable pageable);

    @Query("SELECT wr FROM WorkationReview wr " +
            "JOIN FETCH wr.workationSchedule ws " +
            "JOIN FETCH ws.location " +
            "WHERE ws.location.id = :locationId")
    List<WorkationReview> findByWorkationScheduleLocationIdWithFetchJoin(Long locationId);
}