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
            "LEFT JOIN FETCH ws.review " +
            "WHERE ws.user.id = :userId")
    List<WorkationSchedule> findByUserIdWithFetch(@Param("userId") Long userId);
    void deleteByUserIdAndId(Long userId, Long scheduleId);
    List<WorkationSchedule> findByEndDateBeforeAndIsExpiredFalse(LocalDate now);
    List<WorkationSchedule> findByUserIdAndIsExpiredTrueAndReviewIsNull(Long userId);
}
