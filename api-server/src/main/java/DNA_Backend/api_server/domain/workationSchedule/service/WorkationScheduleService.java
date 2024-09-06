package DNA_Backend.api_server.domain.workationSchedule.service;

import static DNA_Backend.api_server.domain.workationSchedule.message.WorkationScheduleExceptionMessage.OVERLAPPING_SCHEDULE;
import static DNA_Backend.api_server.domain.workationSchedule.message.WorkationScheduleExceptionMessage.SCHEDULE_NOT_FOUND;

import DNA_Backend.api_server.domain.location.model.entity.Location;
import DNA_Backend.api_server.domain.location.service.LocationService;
import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.user.service.UserService;
import DNA_Backend.api_server.domain.workationSchedule.dto.WorkationScheduleDto.AllScheduledDatesResponse;
import DNA_Backend.api_server.domain.workationSchedule.dto.WorkationScheduleDto.CreateWorkationScheduleRequest;
import DNA_Backend.api_server.domain.workationSchedule.dto.WorkationScheduleDto.WorkationScheduleResponse;
import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
import DNA_Backend.api_server.domain.workationSchedule.repository.WorkationScheduleRepository;
import DNA_Backend.api_server.global.exception.DnaApplicationException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    public WorkationSchedule findWorkationSchedule(Long scheduleId) {
        return workationScheduleRepository.findById(scheduleId)
                .orElseThrow(()->new DnaApplicationException((SCHEDULE_NOT_FOUND.getValue())));
    }

    // USER - 워케이션 일정 등록
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
                    throw new DnaApplicationException(OVERLAPPING_SCHEDULE.getValue());
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

    // USER - 전체 워케이션 일정 조회
    @Transactional(readOnly = true)
    public List<WorkationScheduleResponse> getAllWorkationSchedules(Long userId) {
        List<WorkationSchedule> workationSchedules = workationScheduleRepository.findByUserIdWithFetch(userId);
        return workationSchedules.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private WorkationScheduleResponse toResponseDto(WorkationSchedule workationSchedule) {
        return new WorkationScheduleResponse(
                workationSchedule.getId(),
                workationSchedule.getLocation().getName().getValue(),
                workationSchedule.getStartDate(),
                workationSchedule.getEndDate(),
                workationSchedule.getReview() != null
        );
    }

    // USER - 워케이션 일정 삭제
    @Transactional
    public void deleteWorkationSchedule(Long userId, Long scheduleId) {
        workationScheduleRepository.deleteByUserIdAndId(userId, scheduleId);
    }

    // USER - 전체 예정된 날짜 조회
    @Transactional(readOnly = true)
    public AllScheduledDatesResponse getAllScheduledDates(Long userId) {
        List<WorkationSchedule> schedules = workationScheduleRepository.findByUserId(userId);
        List<LocalDate> scheduledDates = schedules.stream()
                .flatMap(schedule ->
                        Stream.iterate(schedule.getStartDate(), date -> !date.isAfter(schedule.getEndDate()), date -> date.plusDays(1))
                )
                .collect(Collectors.toList());
        return new AllScheduledDatesResponse(scheduledDates);
    }

    // USER - 만료되고 리뷰 없는 일정 조회
    @Transactional
    public List<WorkationScheduleResponse> getExpiredNoReviewScheduleResponse(Long userId) {
        List<WorkationSchedule> workationSchedules = workationScheduleRepository.findByUserIdAndIsExpiredTrueAndReviewIsNull(userId);
        return workationSchedules.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

}
