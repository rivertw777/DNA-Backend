package TourData.backend.domain.workationSchedule.repository;

import TourData.backend.domain.workationSchedule.model.entity.WorkationSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkationScheduleRepository extends JpaRepository<WorkationSchedule, Long> {
    List<WorkationSchedule> findByUserId(Long userId);

}
