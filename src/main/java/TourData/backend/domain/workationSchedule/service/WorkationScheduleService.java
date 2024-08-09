package TourData.backend.domain.workationSchedule.service;

import static TourData.backend.domain.workationSchedule.exception.WorkationScheduleExceptionMessage.OVERLAPPING_SCHEDULE;
import static TourData.backend.domain.workationSchedule.exception.WorkationScheduleExceptionMessage.SCHEDULE_NOT_FOUND;

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

    // id로 조회
    @Transactional(readOnly = true)
    public WorkationSchedule findWorkationSchedule(Long scheduleId) {
        return workationScheduleRepository.findById(scheduleId)
                .orElseThrow(()->new WorkationScheduleException((SCHEDULE_NOT_FOUND.getMessage())));
    }

    // 사용자 워케이션 일정 등록
    @Transactional
    public void createWorkationSchedule(Long userId, WorkationScheduleCreateRequest requestParam) {
        User user = userService.findUser(userId);
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

    // 사용자 전체 워케이션 일정 조회
    @Transactional(readOnly = true)
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

    // 사용자 단일 워케이션 일정 조회
    @Transactional(readOnly = true)
    public WorkationScheduleResponse getWorkationSchedule(Long userId, Long scheduleId) {
        WorkationSchedule workationSchedule = findWorkationSchedule(userId, scheduleId);
        return toResponse(workationSchedule);
    }

    private WorkationSchedule findWorkationSchedule(Long userId, Long scheduleId) {
        return workationScheduleRepository.findByUserIdAndId(userId, scheduleId)
                .orElseThrow(()->new WorkationScheduleException((SCHEDULE_NOT_FOUND.getMessage())));
    }

    // 사용자 단일 워케이션 일정 삭제
    @Transactional
    public void deleteWorkationSchedule(Long userId, Long scheduleId) {
        workationScheduleRepository.deleteByUser_IdAndId(userId, scheduleId);
    }

    // 사용자 리뷰가 없는 만료된 전체 워케이션 일정 조회
    @Transactional(readOnly = true)
    public List<WorkationScheduleResponse> getExpiredSchedulesWithoutReview(Long userId) {
        List<WorkationSchedule> workationSchedules = workationScheduleRepository.findByUserIdAndIsExpiredTrueAndReviewIsNull(userId);
        return workationSchedules.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

}
