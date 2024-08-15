package DNA_Backend.api_server.domain.workationSchedule.controller;

import DNA_Backend.api_server.domain.workationSchedule.dto.WorkationScheduleDto.CreateWorkationScheduleRequest;
import DNA_Backend.api_server.domain.workationSchedule.dto.WorkationScheduleDto.WorkationScheduleResponse;
import DNA_Backend.api_server.domain.workationSchedule.service.WorkationScheduleService;
import DNA_Backend.api_server.global.security.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WorkationScheduleController {

    private final WorkationScheduleService workationScheduleService;

    @Operation(summary = "사용자 워케이션 일정 등록")
    @PostMapping("/locations/{locationId}/workation-schedules")
    public ResponseEntity<Void> createWorkationSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                        @Valid @PathVariable("locationId") Long locationId,
                                                        @Valid @RequestBody CreateWorkationScheduleRequest requestParam) {
        Long userId = customUserDetails.getUser().getId();
        workationScheduleService.createWorkationSchedule(userId, locationId, requestParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 전체 워케이션 일정 조회")
    @GetMapping("/workation-schedules")
    public ResponseEntity<List<WorkationScheduleResponse>> getAllWorkationSchedules(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUser().getId();
        List<WorkationScheduleResponse> responses = workationScheduleService.getAllWorkationSchedules(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "사용자 단일 워케이션 일정 조회")
    @GetMapping("/workation-schedules/{scheduleId}")
    public ResponseEntity<WorkationScheduleResponse> getWorkationSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                        @Valid @PathVariable("scheduleId") Long scheduleId) {
        Long userId = customUserDetails.getUser().getId();
        WorkationScheduleResponse response = workationScheduleService.getWorkationSchedule(userId, scheduleId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자 워케이션 일정 삭제")
    @DeleteMapping("/workation-schedules/{scheduleId}")
    public ResponseEntity<Void> deleteWorkationSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                        @Valid @PathVariable("scheduleId") Long scheduleId) {
        Long userId = customUserDetails.getUser().getId();
        workationScheduleService.deleteWorkationSchedule(userId, scheduleId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 전체 리뷰 없고 만료된 워케이션 일정 조회")
    @GetMapping("/workation-schedules/unreviewed-expired")
    public ResponseEntity<List<WorkationScheduleResponse>> getAllUnreviewedExpiredSchedules(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUser().getId();
        List<WorkationScheduleResponse> responses = workationScheduleService.getAllUnreviewedExpiredSchedules(userId);
        return ResponseEntity.ok(responses);
    }

}
