package DNA_Backend.api_server.domain.facility.controller;

import DNA_Backend.api_server.domain.facility.dto.response.BookmarkedFacilityResponse;
import DNA_Backend.api_server.domain.facility.dto.response.CheckFacilityBookmarkResponse;
import DNA_Backend.api_server.domain.facility.service.FacilityBookmarkService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/facilities")
public class FacilityController {

    private final FacilityBookmarkService facilityBookmarkService;

    @Operation(summary = "USER - 시설 북마크")
    @PostMapping("/{facilityId}/bookmark")
    public ResponseEntity<Void> bookmarkFacility(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
                                                 @Valid @PathVariable("facilityId") Long facilityId) {
        Long userId = userDetailsCustom.getUser().getId();
        facilityBookmarkService.bookmarkFacility(userId, facilityId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "USER - 시설 북마크 취소")
    @DeleteMapping("/{facilityId}/bookmark")
    public ResponseEntity<Void> unbookmarkFacility(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
                                                   @Valid @PathVariable("facilityId") Long facilityId) {
        Long userId = userDetailsCustom.getUser().getId();
        facilityBookmarkService.unbookmarkFacility(userId, facilityId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "USER - 시설 북마크 여부 확인")
    @GetMapping("/{facilityId}/bookmark")
    public ResponseEntity<CheckFacilityBookmarkResponse> checkFacilityBookmark(
            @AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
            @Valid @PathVariable("facilityId") Long facilityId) {
        Long userId = userDetailsCustom.getUser().getId();
        CheckFacilityBookmarkResponse response = facilityBookmarkService.checkFacilityBookmark(userId, facilityId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "USER - 전체 북마크 시설 조회")
    @GetMapping("/bookmark")
    public ResponseEntity<List<BookmarkedFacilityResponse>> getAllBookmarkedFacilities(
            @AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        Long userId = userDetailsCustom.getUser().getId();
        List<BookmarkedFacilityResponse> responses = facilityBookmarkService.getAllBookmarkedFacilities(userId);
        return ResponseEntity.ok(responses);
    }

}
