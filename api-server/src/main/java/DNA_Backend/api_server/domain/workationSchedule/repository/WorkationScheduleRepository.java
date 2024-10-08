package DNA_Backend.api_server.domain.workationSchedule.repository;

import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkationScheduleRepository extends JpaRepository<WorkationSchedule, Long> {
    List<WorkationSchedule> findByUserId(Long userId);
    @Query("SELECT ws FROM WorkationSchedule ws " +
            "JOIN FETCH ws.location " +
            "LEFT JOIN FETCH ws.workationReview " +
            "WHERE ws.user.id = :userId " +
            "ORDER BY ws.endDate DESC")
    List<WorkationSchedule> findByUserIdWithFetch(@Param("userId") Long userId);
    List<WorkationSchedule> findByEndDateBeforeAndIsExpiredFalse(LocalDate now);
    List<WorkationSchedule> findByUserIdAndIsExpiredTrueAndWorkationReviewIsNull(Long userId);
}
