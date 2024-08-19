package DNA_Backend.api_server.domain.location.controller;

import DNA_Backend.api_server.domain.facility.dto.FacilityDto.FacilityResponse;
import DNA_Backend.api_server.domain.facility.dto.FacilityDto.LocationTotalFacilityCountResponse;
import DNA_Backend.api_server.domain.facility.service.FacilityService;
import DNA_Backend.api_server.domain.location.dto.LocationDto.CheckLocationLikeResponse;
import DNA_Backend.api_server.domain.location.dto.LocationDto.LocationDetailResponse;
import DNA_Backend.api_server.domain.location.dto.LocationDto.LocationLikeCountResponse;
import DNA_Backend.api_server.domain.location.dto.LocationDto.LocationResponse;
import DNA_Backend.api_server.domain.location.dto.LocationWeatherDto.LocationWeatherResponse;
import DNA_Backend.api_server.domain.location.service.LocationLikeService;
import DNA_Backend.api_server.domain.location.service.LocationService;
import DNA_Backend.api_server.domain.review.dto.ReviewDto.ReviewResponse;
import DNA_Backend.api_server.domain.review.service.ReviewService;
import DNA_Backend.api_server.domain.workationSchedule.dto.WorkationScheduleDto;
import DNA_Backend.api_server.domain.workationSchedule.service.WorkationScheduleService;
import DNA_Backend.api_server.global.security.auth.UserDetailsCustom;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;
    private final LocationLikeService locationLikeService;
    private final FacilityService facilityService;
    private final WorkationScheduleService workationScheduleService;
    private final ReviewService reviewService;

    @Operation(summary = "전체 지역 조회")
    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        List<LocationResponse> responses = locationService.getAllLocations();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "단일 지역 상세 조회")
    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDetailResponse> getLocationDetail(@Valid @PathVariable("locationId") Long locationId) {
        LocationDetailResponse response = locationService.getLocationDetail(locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "전체 지역 날씨 조회")
    @GetMapping("/weather")
    public ResponseEntity<List<LocationWeatherResponse>> getAllLocationWeathers() {
        List<LocationWeatherResponse> responses = locationService.getAllLocationWeathers();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "단일 지역 날씨 조회")
    @GetMapping("/{locationId}/weather")
    public ResponseEntity<LocationWeatherResponse> getLocationWeather(@Valid @PathVariable("locationId") Long locationId) {
        LocationWeatherResponse response = locationService.getLocationWeather(locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자 지역 좋아요")
    @PostMapping("/{locationId}/like")
    public ResponseEntity<Void> likeLocation(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
                                             @Valid @PathVariable("locationId") Long locationId) {
        Long userId = userDetailsCustom.getUser().getId();
        locationLikeService.likeLocation(userId, locationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 지역 좋아요 취소")
    @DeleteMapping("/{locationId}/like")
    public ResponseEntity<Void> unlikeLocation(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
                                               @Valid @PathVariable("locationId") Long locationId) {
        Long userId = userDetailsCustom.getUser().getId();
        locationLikeService.unlikeLocation(userId, locationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 지역 좋아요 여부 확인")
    @GetMapping("/{locationId}/like")
    public ResponseEntity<CheckLocationLikeResponse> checkLocationLike(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
                                                                       @Valid @PathVariable("locationId") Long locationId) {
        Long userId = userDetailsCustom.getUser().getId();
        CheckLocationLikeResponse response = locationLikeService.checkLocationLike(userId, locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "단일 지역 좋아요 수 조회")
    @GetMapping("/{locationId}/like/count")
    public ResponseEntity<LocationLikeCountResponse> getLocationLikeCount(@Valid @PathVariable("locationId") Long locationId) {
        LocationLikeCountResponse response = locationLikeService.getLocationLikeCount(locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "시설 검색 by 지역 id & 타입")
    @GetMapping("/{locationId}/facilities/search")
    public ResponseEntity<List<FacilityResponse>> searchFacilitiesByLocationIdAndType(
            @Valid @PathVariable(name = "locationId") Long locationId,
            @Valid @RequestParam(name = "facilityType") String facilityType) {
        List<FacilityResponse> responses = facilityService.searchFacilitiesByLocationIdAndType(locationId, facilityType);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "전체 지역 총 시설 수 조회")
    @GetMapping("/facilities/count")
    public ResponseEntity<List<LocationTotalFacilityCountResponse>> getAllLocationTotalFacilityCounts() {
        List<LocationTotalFacilityCountResponse> responses = facilityService.getAllLocationTotalFacilityCounts();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "단일 지역 총 시설 수 조회")
    @GetMapping("/{locationId}/facilities/count")
    public ResponseEntity<LocationTotalFacilityCountResponse> getLocationTotalFacilityCount(
            @Valid @PathVariable("locationId") Long locationId) {
        LocationTotalFacilityCountResponse response = facilityService.getLocationTotalFacilityCount(locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자 워케이션 일정 등록")
    @PostMapping("/{locationId}/workation-schedules")
    public ResponseEntity<Void> createWorkationSchedule(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
                                                        @Valid @PathVariable("locationId") Long locationId,
                                                        @Valid @RequestBody WorkationScheduleDto.CreateWorkationScheduleRequest requestParam) {
        Long userId = userDetailsCustom.getUser().getId();
        workationScheduleService.createWorkationSchedule(userId, locationId, requestParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "단일 지역 워케이션 리뷰 조회")
    @GetMapping("/{locationId}/reviews")
    public ResponseEntity<Page<ReviewResponse>> getLocationReviews(
            @Valid @PathVariable(name = "locationId") Long locationId, Pageable pageable) {
        Page<ReviewResponse> responses = reviewService.getLocationReviews(locationId, pageable);
        return ResponseEntity.ok(responses);
    }

}
