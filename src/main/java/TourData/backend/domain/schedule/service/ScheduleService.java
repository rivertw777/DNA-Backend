package TourData.backend.domain.schedule.service;

import static TourData.backend.domain.schedule.exception.ScheduleExceptionMessage.START_DATE_AFTER_END_DATE;

import TourData.backend.domain.schedule.dto.ScheduleDto.ScheduleCreateRequest;
import TourData.backend.domain.schedule.exception.ScheduleException;
import TourData.backend.domain.schedule.model.entity.Schedule;
import TourData.backend.domain.schedule.repository.ScheduleRepository;
import TourData.backend.domain.user.model.entity.User;
import TourData.backend.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    @Transactional
    public void createSchedule(Long userId, ScheduleCreateRequest requestParam) {
        User user = userService.findUser(userId);
        validateScheduleDates(requestParam.startDate(), requestParam.endDate());
        saveSchedule(user, requestParam);
    }

    private void validateScheduleDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new ScheduleException(START_DATE_AFTER_END_DATE.getMessage());
        }
    }

    private void saveSchedule(User user, ScheduleCreateRequest requestParam) {
        Schedule schedule = Schedule.createSchedule(user, requestParam);
        scheduleRepository.save(schedule);
    }

}
