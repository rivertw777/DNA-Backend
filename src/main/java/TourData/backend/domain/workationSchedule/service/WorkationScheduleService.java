package TourData.backend.domain.workationSchedule.service;

import static TourData.backend.domain.workationSchedule.exception.WorkationScheduleExceptionMessage.START_DATE_AFTER_END_DATE;

import TourData.backend.domain.workationSchedule.dto.WorkationScheduleDto.WorkationScheduleCreateRequest;
import TourData.backend.domain.workationSchedule.dto.WorkationScheduleDto.WorkationScheduleResponse;
import TourData.backend.domain.workationSchedule.exception.WorkationScheduleException;
import TourData.backend.domain.workationSchedule.model.entity.WorkationSchedule;
import TourData.backend.domain.workationSchedule.repository.WorkationScheduleRepository;
import TourData.backend.domain.user.model.entity.User;
import TourData.backend.domain.user.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkationScheduleService {

    private final WorkationScheduleRepository workationScheduleRepository;
    private final UserService userService;

    // 사용자 워케이션 일정 등록
    @Transactional
    public void createWorkationSchedule(Long userId, WorkationScheduleCreateRequest requestParam) {
        User user = userService.findUser(userId);
        validateScheduleDates(requestParam.startDate(), requestParam.endDate());
        saveSchedule(user, requestParam);
    }

    private void validateScheduleDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new WorkationScheduleException(START_DATE_AFTER_END_DATE.getMessage());
        }
    }

    private void saveSchedule(User user, WorkationScheduleCreateRequest requestParam) {
        WorkationSchedule workationSchedule = WorkationSchedule.createWorkationSchedule(user, requestParam);
        workationScheduleRepository.save(workationSchedule);
    }

    // 사용자 워케이션 일정 조회
    public List<WorkationScheduleResponse> getAllWorkationSchedules(Long userId) {
        List<WorkationSchedule> workationSchedules = workationScheduleRepository.findByUserId(userId);
        return workationSchedules.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private WorkationScheduleResponse toResponse(WorkationSchedule workationSchedule) {
        return new WorkationScheduleResponse(
                workationSchedule.getId(),
                workationSchedule.getLocationName(),
                workationSchedule.getStartDate(),
                workationSchedule.getEndDate()
        );
    }

}
