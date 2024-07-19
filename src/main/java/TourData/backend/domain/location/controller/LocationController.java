package TourData.backend.domain.location.controller;

import TourData.backend.domain.location.dto.LocationDto.LocationLikeCheckResponse;
import TourData.backend.domain.location.dto.LocationDto.LocationLikeCountResponse;
import TourData.backend.domain.location.dto.LocationDto.LocationResponse;
import TourData.backend.domain.location.service.LocationService;
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
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    @Operation(summary = "지역 전체 조회")
    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        List<LocationResponse> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @Operation(summary = "지역 좋아요")
    @PostMapping("/{locationId}/like")
    public ResponseEntity<Void> likeLocation(@AuthenticationPrincipal(expression = "username") String username,
                                             @Valid @PathVariable("locationId") Long locationId) {
        locationService.likeLocation(username, locationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "지역 좋아요 취소")
    @DeleteMapping("/{locationId}/like")
    public ResponseEntity<Void> unlikeLocation(@AuthenticationPrincipal(expression = "username") String username,
                                               @Valid @PathVariable("locationId") Long locationId) {
        locationService.unlikeLocation(username, locationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "지역 좋아요 여부 확인")
    @GetMapping("/{locationId}/like")
    public ResponseEntity<LocationLikeCheckResponse> checkLocationLike(@AuthenticationPrincipal(expression = "username") String username,
                                                                       @Valid @PathVariable("locationId") Long locationId) {
        LocationLikeCheckResponse response = locationService.checkLocationLike(username, locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "지역 좋아요 수 조회")
    @GetMapping("/{locationId}/like/count")
    public ResponseEntity<LocationLikeCountResponse> getLocationLikeCount(@Valid @PathVariable("locationId") Long locationId) {
        LocationLikeCountResponse response = locationService.getLocationLikeCount(locationId);
        return ResponseEntity.ok(response);
    }

}
