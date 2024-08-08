package TourData.backend.domain.schedule.repository;

import TourData.backend.domain.schedule.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
