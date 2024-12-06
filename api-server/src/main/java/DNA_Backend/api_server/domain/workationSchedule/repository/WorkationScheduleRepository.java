package DNA_Backend.api_server.domain.workationSchedule.repository;

import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkationScheduleRepository extends JpaRepository<WorkationSchedule, Long> {
    @Query("SELECT ws FROM WorkationSchedule ws " +
            "JOIN FETCH ws.workationReview " +
            "WHERE ws.user.id = :userId")
    List<WorkationSchedule> findByUserId(Long userId);

    @Query("SELECT ws FROM WorkationSchedule ws " +
            "JOIN FETCH ws.location " +
            "LEFT JOIN FETCH ws.workationReview " +
            "WHERE ws.user.id = :userId " +
            "ORDER BY ws.createdAt DESC")
    List<WorkationSchedule> findByUserIdWithFetchJoin(Long userId);

    @Query("SELECT ws FROM WorkationSchedule ws " +
            "JOIN FETCH ws.location " +
            "JOIN FETCH ws.user u " +
            "JOIN FETCH u.roles " +
            "LEFT JOIN FETCH ws.workationReview wr " +
            "WHERE ws.endDate < :now " +
            "AND ws.isExpired = false")
    List<WorkationSchedule> findByEndDateBeforeAndIsExpiredFalseWithFetchJoin(LocalDate now);

    @Query("SELECT ws FROM WorkationSchedule ws " +
            "JOIN FETCH ws.location " +
            "LEFT JOIN FETCH ws.workationReview wr " +
            "WHERE ws.user.id = :userId " +
            "AND ws.isExpired = true " +
            "AND wr IS NULL " +
            "ORDER BY ws.createdAt DESC")
    List<WorkationSchedule> findByUserIdAndIsExpiredTrueAndWorkationReviewIsNullWithFetchJoin(Long userId);
}
