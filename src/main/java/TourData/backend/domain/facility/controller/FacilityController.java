package TourData.backend.domain.facility.controller;

import TourData.backend.domain.facility.dto.FacilityDto.BookmarkedFacilityResponse;
import TourData.backend.domain.facility.dto.FacilityDto.FacilityBookmarkCheckResponse;
import TourData.backend.domain.facility.dto.FacilityDto.FacilitySearchResponse;
import TourData.backend.domain.facility.service.FacilityBookmarkService;
import TourData.backend.domain.facility.service.FacilityService;
import TourData.backend.global.security.auth.CustomUserDetails;
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
@RequestMapping("/api/facilities")
public class FacilityController {

    private final FacilityService facilityService;
    private final FacilityBookmarkService facilityBookmarkService;

    @Operation(summary = "시설 검색 by 위도, 경도")
    @GetMapping("/search")
    public ResponseEntity<List<FacilitySearchResponse>> searchFacilities(
            @Valid @RequestParam(name = "latMin") Double latMin, @Valid @RequestParam(name = "latMax") Double latMax,
            @Valid @RequestParam(name = "lngMin") Double lngMin, @Valid @RequestParam(name = "lngMax") Double lngMax,
            @Valid @RequestParam(name = "facilityType") String facilityType) {
        List<FacilitySearchResponse> responses = facilityService.searchFacilities(latMin, latMax, lngMin, lngMax, facilityType);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "시설 검색 by 지역 코드")
    @GetMapping("/search/code")
    public ResponseEntity<List<FacilitySearchResponse>> searchFacilitiesByLocationCode(
            @Valid @RequestParam(name = "locationCode") String locationCode,
            @Valid @RequestParam(name = "facilityType") String facilityType) {
        List<FacilitySearchResponse> responses = facilityService.searchFacilitiesByLocationCode(locationCode, facilityType);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "사용자 시설 북마크")
    @PostMapping("{facilityId}/bookmarks")
    public ResponseEntity<Void> bookmarkFacility(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @Valid @PathVariable("facilityId") Long facilityId) {
        Long userId = customUserDetails.getUser().getId();
        facilityBookmarkService.bookmarkFacility(userId, facilityId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 시설 북마크 취소")
    @DeleteMapping("{facilityId}/bookmarks")
    public ResponseEntity<Void> unbookmarkFacility(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                   @Valid @PathVariable("facilityId") Long facilityId) {
        Long userId = customUserDetails.getUser().getId();
        facilityBookmarkService.unbookmarkFacility(userId, facilityId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 시설 북마크 여부 확인")
    @GetMapping("{facilityId}/bookmarks")
    public ResponseEntity<FacilityBookmarkCheckResponse> checkFacilityBookmark(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @PathVariable("facilityId") Long facilityId) {
        Long userId = customUserDetails.getUser().getId();
        FacilityBookmarkCheckResponse response = facilityBookmarkService.checkFacilityBookmark(userId, facilityId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자 북마크 시설 전체 조회")
    @GetMapping("/bookmarks")
    public ResponseEntity<List<BookmarkedFacilityResponse>> getAllBookmarks(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUser().getId();
        List<BookmarkedFacilityResponse> responses = facilityBookmarkService.getAllBookmarks(userId);
        return ResponseEntity.ok(responses);
    }

}
