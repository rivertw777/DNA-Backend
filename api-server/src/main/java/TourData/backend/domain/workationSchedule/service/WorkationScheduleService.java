package TourData.backend.domain.workationSchedule.service;

import static TourData.backend.domain.workationSchedule.exception.WorkationScheduleExceptionMessage.OVERLAPPING_SCHEDULE;
import static TourData.backend.domain.workationSchedule.exception.WorkationScheduleExceptionMessage.SCHEDULE_NOT_FOUND;

import TourData.backend.domain.location.model.Location;
import TourData.backend.domain.location.service.LocationService;
import TourData.backend.domain.workationSchedule.dto.WorkationScheduleDto.CreateWorkationScheduleRequest;
import TourData.backend.domain.workationSchedule.dto.WorkationScheduleDto.WorkationScheduleResponse;
import TourData.backend.domain.workationSchedule.exception.WorkationScheduleException;
import TourData.backend.domain.workationSchedule.model.WorkationSchedule;
import TourData.backend.domain.workationSchedule.repository.WorkationScheduleRepository;
import TourData.backend.domain.user.model.User;
import TourData.backend.domain.user.service.UserService;
import java.time.LocalDate;
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
    private final LocationService locationService;

    // id로 조회
    @Transactional(readOnly = true)
    public WorkationSchedule findWorkationSchedule(Long scheduleId) {
        return workationScheduleRepository.findById(scheduleId)
                .orElseThrow(()->new WorkationScheduleException((SCHEDULE_NOT_FOUND.getMessage())));
    }

    // 사용자 워케이션 일정 등록
    @Transactional
    public void createWorkationSchedule(Long userId, Long locationId, CreateWorkationScheduleRequest requestParam) {
        User user = userService.findUser(userId);
        Location location = locationService.findLocation(locationId);
        validateScheduleOverlap(userId, requestParam.startDate(), requestParam.endDate());
        saveSchedule(user, location, requestParam);
    }

    private void validateScheduleOverlap(Long userId, LocalDate startDate, LocalDate endDate) {
        List<WorkationSchedule> schedules = workationScheduleRepository.findByUserId(userId);

        schedules.stream()
                .filter(schedule -> isOverlapping(schedule.getStartDate(), schedule.getEndDate(), startDate, endDate))
                .findAny()
                .ifPresent(schedule -> {
                    throw new WorkationScheduleException(OVERLAPPING_SCHEDULE.getMessage());
                });
    }

    private boolean isOverlapping(LocalDate existingStart, LocalDate existingEnd, LocalDate newStart, LocalDate newEnd) {
        return (existingStart.isBefore(newEnd) && existingEnd.isAfter(newStart)) ||
                (existingStart.isEqual(newEnd) || existingEnd.isEqual(newStart));
    }

    private void saveSchedule(User user, Location location, CreateWorkationScheduleRequest requestParam) {
        WorkationSchedule workationSchedule = WorkationSchedule.createWorkationSchedule(user, location, requestParam.startDate(), requestParam.endDate());
        workationScheduleRepository.save(workationSchedule);
    }

    // 사용자 전체 워케이션 일정 조회
    @Transactional(readOnly = true)
    public List<WorkationScheduleResponse> getAllWorkationSchedules(Long userId) {
        List<WorkationSchedule> workationSchedules = workationScheduleRepository.findByUserId(userId);
        return workationSchedules.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    // 사용자 단일 워케이션 일정 조회
    @Transactional(readOnly = true)
    public WorkationScheduleResponse getWorkationSchedule(Long userId, Long scheduleId) {
        WorkationSchedule workationSchedule = findWorkationSchedule(userId, scheduleId);
        return toResponseDto(workationSchedule);
    }

    private WorkationSchedule findWorkationSchedule(Long userId, Long scheduleId) {
        return workationScheduleRepository.findByUserIdAndId(userId, scheduleId)
                .orElseThrow(()->new WorkationScheduleException((SCHEDULE_NOT_FOUND.getMessage())));
    }

    // 사용자 워케이션 일정 삭제
    @Transactional
    public void deleteWorkationSchedule(Long userId, Long scheduleId) {
        workationScheduleRepository.deleteByUserIdAndId(userId, scheduleId);
    }

    // 사용자 전체 리뷰 없고 만료된 워케이션 일정 조회
    @Transactional(readOnly = true)
    public List<WorkationScheduleResponse> getAllUnreviewedExpiredSchedules(Long userId) {
        List<WorkationSchedule> workationSchedules = workationScheduleRepository.findByUserIdAndIsExpiredTrueAndReviewIsNull(userId);
        return workationSchedules.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private WorkationScheduleResponse toResponseDto(WorkationSchedule workationSchedule) {
        return new WorkationScheduleResponse(
                workationSchedule.getId(),
                workationSchedule.getLocation().getName().getValue(),
                workationSchedule.getStartDate(),
                workationSchedule.getEndDate()
        );
    }

}
