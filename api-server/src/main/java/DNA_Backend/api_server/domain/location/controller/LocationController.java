package DNA_Backend.api_server.domain.location.controller;

import DNA_Backend.api_server.domain.location.dto.request.CreateWorkationScheduleRequest;
import DNA_Backend.api_server.domain.workationSchedule.service.WorkationScheduleService;
import DNA_Backend.api_server.common.security.auth.UserDetailsCustom;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locations")
public class LocationController {

    private final WorkationScheduleService workationScheduleService;

    @Operation(summary = "USER - 워케이션 일정 등록")
    @PostMapping("/{locationId}/workation-schedules")
    public ResponseEntity<Void> createWorkationSchedule(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
                                                        @Valid @PathVariable("locationId") Long locationId,
                                                        @Valid @RequestBody CreateWorkationScheduleRequest requestParam) {
        Long userId = userDetailsCustom.getUser().getId();
        workationScheduleService.createWorkationSchedule(userId, locationId, requestParam);
        return ResponseEntity.ok().build();
    }

}
