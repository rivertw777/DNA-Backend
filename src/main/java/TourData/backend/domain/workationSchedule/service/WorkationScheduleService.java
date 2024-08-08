package TourData.backend.domain.workationSchedule.service;

import static TourData.backend.domain.workationSchedule.exception.WorkationScheduleExceptionMessage.OVERLAPPING_SCHEDULE;

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
        // 일정 중복 검증
        validateScheduleOverlap(userId, requestParam.startDate(), requestParam.endDate());
        saveSchedule(user, requestParam);
    }

    private void validateScheduleOverlap(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<WorkationSchedule> schedules = workationScheduleRepository.findByUserId(userId);

        schedules.stream()
                .filter(schedule -> isOverlapping(schedule.getStartDate(), schedule.getEndDate(), startDate, endDate))
                .findAny()
                .ifPresent(schedule -> {
                    throw new WorkationScheduleException(OVERLAPPING_SCHEDULE.getMessage());
                });
    }

    private boolean isOverlapping(LocalDateTime existingStart, LocalDateTime existingEnd, LocalDateTime newStart, LocalDateTime newEnd) {
        return (existingStart.isBefore(newEnd) && existingEnd.isAfter(newStart)) ||
                (existingStart.isEqual(newEnd) || existingEnd.isEqual(newStart));
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
