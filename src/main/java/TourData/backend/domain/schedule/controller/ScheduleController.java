package TourData.backend.domain.schedule.controller;

import TourData.backend.domain.schedule.dto.ScheduleDto.ScheduleCreateRequest;
import TourData.backend.domain.schedule.service.ScheduleService;
import TourData.backend.global.security.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "사용자 일정 등록")
    @PostMapping
    public ResponseEntity<Void> createSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                               @Valid @RequestBody ScheduleCreateRequest requestParam) {
        Long userId = customUserDetails.getUser().getId();
        scheduleService.createSchedule(userId, requestParam);
        return ResponseEntity.ok().build();
    }

}
