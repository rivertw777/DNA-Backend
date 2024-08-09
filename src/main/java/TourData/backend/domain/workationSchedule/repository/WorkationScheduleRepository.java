package TourData.backend.domain.workationSchedule.repository;

import TourData.backend.domain.workationSchedule.model.entity.WorkationSchedule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkationScheduleRepository extends JpaRepository<WorkationSchedule, Long> {
    List<WorkationSchedule> findByUserId(Long userId);
    Optional<WorkationSchedule> findByUserIdAndId(Long userId, Long scheduleId);
    void deleteByUser_IdAndId(Long userId, Long scheduleId);
}
