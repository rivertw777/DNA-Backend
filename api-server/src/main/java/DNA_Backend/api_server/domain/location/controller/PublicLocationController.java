package DNA_Backend.api_server.domain.location.controller;

import DNA_Backend.api_server.domain.facility.dto.response.FacilityResponse;
import DNA_Backend.api_server.domain.facility.dto.response.LocationTotalFacilityCountResponse;
import DNA_Backend.api_server.domain.facility.service.FacilityService;
import DNA_Backend.api_server.domain.location.dto.response.LocationDetailResponse;
import DNA_Backend.api_server.domain.location.dto.response.LocationResponse;
import DNA_Backend.api_server.domain.location.dto.response.LocationWeatherResponse;
import DNA_Backend.api_server.domain.location.service.LocationService;
import DNA_Backend.api_server.domain.workationOffice.dto.response.WorkationOfficeResponse;
import DNA_Backend.api_server.domain.workationOffice.service.WorkationOfficeService;
import DNA_Backend.api_server.domain.workationReview.dto.response.WorkationReviewResponse;
import DNA_Backend.api_server.domain.workationReview.service.WorkationReviewService;
import DNA_Backend.api_server.domain.workationReview.utils.WorkationReviewPage;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/locations")
public class PublicLocationController {

    private final LocationService locationService;
    private final FacilityService facilityService;
    private final WorkationOfficeService workationOfficeService;
    private final WorkationReviewService workationReviewService;

    @Operation(summary = "PUBLIC - 전체 지역 조회")
    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        List<LocationResponse> responses = locationService.getAllLocations();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "PUBLIC - 단일 지역 상세 조회")
    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDetailResponse> getLocationDetail(@Valid @PathVariable("locationId") Long locationId) {
        LocationDetailResponse response = locationService.getLocationDetail(locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "PUBLIC - 전체 지역 날씨 조회")
    @GetMapping("/weather")
    public ResponseEntity<List<LocationWeatherResponse>> getAllLocationWeathers() {
        List<LocationWeatherResponse> responses = locationService.getAllLocationWeathers();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "PUBLIC - 단일 지역 날씨 조회")
    @GetMapping("/{locationId}/weather")
    public ResponseEntity<LocationWeatherResponse> getLocationWeather(@Valid @PathVariable("locationId") Long locationId) {
        LocationWeatherResponse response = locationService.getLocationWeather(locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "PUBLIC - 시설 검색 by 지역 Id & 타입")
    @GetMapping("/{locationId}/facilities/search")
    public ResponseEntity<List<FacilityResponse>> searchFacilitiesByLocationIdAndType(
            @Valid @PathVariable(name = "locationId") Long locationId,
            @Valid @RequestParam(name = "facilityType") String facilityType) {
        List<FacilityResponse> responses = facilityService.searchFacilitiesByLocationIdAndType(locationId, facilityType);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "PUBLIC - 워케이션 오피스 검색 by 지역 Id")
    @GetMapping("/{locationId}/workation-offices/search")
    public ResponseEntity<List<WorkationOfficeResponse>> searchWorkationOfficesByLocationId(
            @Valid @PathVariable(name = "locationId") Long locationId) {
        List<WorkationOfficeResponse> responses = workationOfficeService.searchWorkationOfficesByLocationId(locationId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "PUBLIC - 전체 지역 총 시설 수 조회")
    @GetMapping("/facilities/count")
    public ResponseEntity<List<LocationTotalFacilityCountResponse>> getAllLocationTotalFacilityCounts() {
        List<LocationTotalFacilityCountResponse> responses = facilityService.getAllLocationTotalFacilityCounts();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "PUBLIC - 단일 지역 총 시설 수 조회")
    @GetMapping("/{locationId}/facilities/count")
    public ResponseEntity<LocationTotalFacilityCountResponse> getLocationTotalFacilityCount(
            @Valid @PathVariable("locationId") Long locationId) {
        LocationTotalFacilityCountResponse response = facilityService.getLocationTotalFacilityCount(locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "PUBLIC - 전체 지역 워케이션 리뷰 조회")
    @GetMapping("/workation-reviews")
    public ResponseEntity<WorkationReviewPage<WorkationReviewResponse>> getAllLocationWorkationReviews(Pageable pageable) {
        WorkationReviewPage<WorkationReviewResponse> responses = workationReviewService.getAllLocationWorkationReviews(pageable);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "PUBLIC - 단일 지역 워케이션 리뷰 조회")
    @GetMapping("/{locationId}/workation-reviews")
    public ResponseEntity<WorkationReviewPage<WorkationReviewResponse>> getLocationWorkationReviews(
            @Valid @PathVariable(name = "locationId") Long locationId, Pageable pageable) {
        WorkationReviewPage<WorkationReviewResponse> responses = workationReviewService.getLocationWorkationReviews(pageable, locationId);
        return ResponseEntity.ok(responses);
    }

}
