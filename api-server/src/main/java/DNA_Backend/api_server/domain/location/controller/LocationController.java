package DNA_Backend.api_server.domain.location.controller;

import DNA_Backend.api_server.domain.facility.dto.FacilityDto.FacilityResponse;
import DNA_Backend.api_server.domain.facility.dto.FacilityDto.LocationTotalFacilityCountResponse;
import DNA_Backend.api_server.domain.facility.service.FacilityService;
import DNA_Backend.api_server.domain.location.dto.LocationDto.LocationDetailResponse;
import DNA_Backend.api_server.domain.location.dto.LocationDto.LocationResponse;
import DNA_Backend.api_server.domain.location.dto.LocationWeatherDto.LocationWeatherResponse;
import DNA_Backend.api_server.domain.location.service.LocationService;
import DNA_Backend.api_server.domain.workationReview.dto.WorkationReviewResponse;
import DNA_Backend.api_server.domain.workationReview.service.WorkationReviewService;
import DNA_Backend.api_server.domain.workationReview.utils.WorkationReviewPage;
import DNA_Backend.api_server.domain.workationSchedule.dto.WorkationScheduleDto;
import DNA_Backend.api_server.domain.workationSchedule.service.WorkationScheduleService;
import DNA_Backend.api_server.global.security.auth.UserDetailsCustom;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final FacilityService facilityService;
    private final WorkationScheduleService workationScheduleService;
    private final WorkationReviewService workationReviewService;

    @Operation(summary = "PUBLIC - 전체 지역 조회")
    @GetMapping("/api/public/locations")
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        List<LocationResponse> responses = locationService.getAllLocations();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "PUBLIC - 단일 지역 상세 단일 조회")
    @GetMapping("/api/public/locations/{locationId}")
    public ResponseEntity<LocationDetailResponse> getLocationDetail(@Valid @PathVariable("locationId") Long locationId) {
        LocationDetailResponse response = locationService.getLocationDetail(locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "PUBLIC - 전체 지역 날씨 조회")
    @GetMapping("/api/public/locations/weather")
    public ResponseEntity<List<LocationWeatherResponse>> getAllLocationWeathers() {
        List<LocationWeatherResponse> responses = locationService.getAllLocationWeathers();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "PUBLIC - 단일 지역 날씨 조회")
    @GetMapping("/api/public/locations/{locationId}/weather")
    public ResponseEntity<LocationWeatherResponse> getLocationWeather(@Valid @PathVariable("locationId") Long locationId) {
        LocationWeatherResponse response = locationService.getLocationWeather(locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "PUBLIC - 시설 검색 by 지역 Id & 타입")
    @GetMapping("/api/public/locations/{locationId}/facilities/search")
    public ResponseEntity<List<FacilityResponse>> searchFacilitiesByLocationIdAndType(
            @Valid @PathVariable(name = "locationId") Long locationId,
            @Valid @RequestParam(name = "facilityType") String facilityType) {
        List<FacilityResponse> responses = facilityService.searchFacilitiesByLocationIdAndType(locationId, facilityType);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "PUBLIC - 전체 지역 총 시설 수 조회")
    @GetMapping("/api/public/locations/facilities/count")
    public ResponseEntity<List<LocationTotalFacilityCountResponse>> getAllLocationTotalFacilityCounts() {
        List<LocationTotalFacilityCountResponse> responses = facilityService.getAllLocationTotalFacilityCounts();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "PUBLIC - 단일 지역 총 시설 수 조회")
    @GetMapping("/api/public/locations/{locationId}/facilities/count")
    public ResponseEntity<LocationTotalFacilityCountResponse> getLocationTotalFacilityCount(
            @Valid @PathVariable("locationId") Long locationId) {
        LocationTotalFacilityCountResponse response = facilityService.getLocationTotalFacilityCount(locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "USER - 워케이션 일정 등록")
    @PostMapping("/api/locations/{locationId}/workation-schedules")
    public ResponseEntity<Void> createWorkationSchedule(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
                                                        @Valid @PathVariable("locationId") Long locationId,
                                                        @Valid @RequestBody WorkationScheduleDto.CreateWorkationScheduleRequest requestParam) {
        Long userId = userDetailsCustom.getUser().getId();
        workationScheduleService.createWorkationSchedule(userId, locationId, requestParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PUBLIC - 단일 지역 워케이션 리뷰 조회")
    @GetMapping("/api/public/locations/{locationId}/reviews")
    public ResponseEntity<WorkationReviewPage<WorkationReviewResponse>> getLocationWorkationReviews(
            @Valid @PathVariable(name = "locationId") Long locationId, Pageable pageable) {
        WorkationReviewPage<WorkationReviewResponse> responses = workationReviewService.getLocationWorkationReviews(pageable, locationId);
        return ResponseEntity.ok(responses);
    }

}
