package DNA_Backend.api_server.domain.facility.controller;

import DNA_Backend.api_server.domain.facility.dto.FacilityDto.BookmarkedFacilityResponse;
import DNA_Backend.api_server.domain.facility.dto.FacilityDto.CheckFacilityBookmarkResponse;
import DNA_Backend.api_server.domain.facility.dto.FacilityDto.FacilityResponse;
import DNA_Backend.api_server.domain.facility.dto.FacilityDto.LocationTotalFacilityCountResponse;
import DNA_Backend.api_server.domain.facility.service.FacilityBookmarkService;
import DNA_Backend.api_server.domain.facility.service.FacilityService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FacilityController {

    private final FacilityService facilityService;
    private final FacilityBookmarkService facilityBookmarkService;

    @Operation(summary = "시설 검색 by 위도, 경도 & 타입")
    @GetMapping("/facilities/search")
    public ResponseEntity<List<FacilityResponse>> searchFacilities(
            @Valid @RequestParam(name = "latMin") Double latMin, @Valid @RequestParam(name = "latMax") Double latMax,
            @Valid @RequestParam(name = "lngMin") Double lngMin, @Valid @RequestParam(name = "lngMax") Double lngMax,
            @Valid @RequestParam(name = "facilityType") String facilityType) {
        List<FacilityResponse> responses = facilityService.searchFacilities(latMin, latMax, lngMin, lngMax, facilityType);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "시설 검색 by 지역 id & 타입")
    @GetMapping("/locations/{locationId}/facilities/search")
    public ResponseEntity<List<FacilityResponse>> searchFacilitiesByLocationIdAndType(
            @Valid @PathVariable(name = "locationId") Long locationId,
            @Valid @RequestParam(name = "facilityType") String facilityType) {
        List<FacilityResponse> responses = facilityService.searchFacilitiesByLocationIdAndType(locationId, facilityType);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "사용자 시설 북마크")
    @PostMapping("/facilities/{facilityId}/bookmark")
    public ResponseEntity<Void> bookmarkFacility(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @Valid @PathVariable("facilityId") Long facilityId) {
        Long userId = customUserDetails.getUser().getId();
        facilityBookmarkService.bookmarkFacility(userId, facilityId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 시설 북마크 취소")
    @DeleteMapping("/facilities/{facilityId}/bookmark")
    public ResponseEntity<Void> unbookmarkFacility(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                   @Valid @PathVariable("facilityId") Long facilityId) {
        Long userId = customUserDetails.getUser().getId();
        facilityBookmarkService.unbookmarkFacility(userId, facilityId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 시설 북마크 여부 확인")
    @GetMapping("/facilities/{facilityId}/bookmark")
    public ResponseEntity<CheckFacilityBookmarkResponse> checkFacilityBookmark(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @PathVariable("facilityId") Long facilityId) {
        Long userId = customUserDetails.getUser().getId();
        CheckFacilityBookmarkResponse response = facilityBookmarkService.checkFacilityBookmark(userId, facilityId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자 전체 북마크 시설 조회")
    @GetMapping("/facilities/bookmark")
    public ResponseEntity<List<BookmarkedFacilityResponse>> getAllBookmarkedFacilities(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUser().getId();
        List<BookmarkedFacilityResponse> responses = facilityBookmarkService.getAllBookmarkedFacilities(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "전체 지역 총 시설 수 조회")
    @GetMapping("/locations/facilities/count")
    public ResponseEntity<List<LocationTotalFacilityCountResponse>> getTotalFacilityCountsForAllLocations() {
        List<LocationTotalFacilityCountResponse> responses = facilityService.getTotalFacilityCountsForAllLocations();
        return ResponseEntity.ok(responses);
    }

}
