package DNA_Backend.api_server.domain.workationSchedule.controller;

import DNA_Backend.api_server.domain.review.dto.ReviewDto;
import DNA_Backend.api_server.domain.review.service.ReviewService;
import DNA_Backend.api_server.domain.workationSchedule.dto.WorkationScheduleDto.AllScheduledDatesResponse;
import DNA_Backend.api_server.domain.workationSchedule.dto.WorkationScheduleDto.WorkationScheduleResponse;
import DNA_Backend.api_server.domain.workationSchedule.service.WorkationScheduleService;
import DNA_Backend.api_server.global.security.auth.UserDetailsCustom;
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
@RequestMapping("/api/workation-schedules")
public class WorkationScheduleController {

    private final WorkationScheduleService workationScheduleService;
    private final ReviewService reviewService;

    @Operation(summary = "사용자 전체 워케이션 일정 조회")
    @GetMapping
    public ResponseEntity<List<WorkationScheduleResponse>> getAllWorkationSchedules(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        Long userId = userDetailsCustom.getUser().getId();
        List<WorkationScheduleResponse> responses = workationScheduleService.getAllWorkationSchedules(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "사용자 워케이션 일정 삭제")
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteWorkationSchedule(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
                                                        @Valid @PathVariable("scheduleId") Long scheduleId) {
        Long userId = userDetailsCustom.getUser().getId();
        workationScheduleService.deleteWorkationSchedule(userId, scheduleId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 전체 일정 날짜 조회")
    @GetMapping("/dates")
    public ResponseEntity<AllScheduledDatesResponse> getAllScheduledDates(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        Long userId = userDetailsCustom.getUser().getId();
        AllScheduledDatesResponse response = workationScheduleService.getAllScheduledDates(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자 워케이션 리뷰 작성")
    @PostMapping("/{scheduleId}/reviews")
    public ResponseEntity<Void> writeReview(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
                                            @Valid @PathVariable("scheduleId") Long scheduleId,
                                            @Valid @RequestBody ReviewDto.WriteReviewRequest reqeustParam) {
        Long userId = userDetailsCustom.getUser().getId();
        reviewService.writeReview(userId, scheduleId, reqeustParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 만료되고 리뷰 없는 일정 조회")
    @GetMapping("/expired-no-review")
    public ResponseEntity<List<WorkationScheduleResponse>> getExpiredNoReviewScheduleResponse(
            @AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        Long userId = userDetailsCustom.getUser().getId();
        List<WorkationScheduleResponse> responses = workationScheduleService.getExpiredNoReviewScheduleResponse(userId);
        return ResponseEntity.ok(responses);
    }

}
