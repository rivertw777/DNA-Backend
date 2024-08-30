package DNA_Backend.api_server.domain.workationSchedule.repository;

import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkationScheduleRepository extends JpaRepository<WorkationSchedule, Long> {
    List<WorkationSchedule> findByUserId(Long userId);
    Optional<WorkationSchedule> findByUserIdAndId(Long userId, Long scheduleId);
    void deleteByUserIdAndId(Long userId, Long scheduleId);
    List<WorkationSchedule> findByEndDateBeforeAndIsExpiredFalse(LocalDate now);
}
