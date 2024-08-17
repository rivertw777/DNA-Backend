package DNA_Backend.api_server.domain.location.controller;

import DNA_Backend.api_server.domain.location.dto.LocationDto.CheckLocationLikeResponse;
import DNA_Backend.api_server.domain.location.dto.LocationDto.LocationLikeCountResponse;
import DNA_Backend.api_server.domain.location.dto.LocationDto.LocationResponse;
import DNA_Backend.api_server.domain.location.dto.LocationWeatherDto.LocationWeatherResponse;
import DNA_Backend.api_server.domain.location.service.LocationLikeService;
import DNA_Backend.api_server.domain.location.service.LocationService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;
    private final LocationLikeService locationLikeService;

    @Operation(summary = "전체 지역 조회")
    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        List<LocationResponse> responses = locationService.getAllLocations();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "단일 지역 조회")
    @GetMapping("/{locationId}")
    public ResponseEntity<LocationResponse> getLocation(@PathVariable("locationId") Long locationId) {
        LocationResponse response = locationService.getLocation(locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "전체 지역 날씨 조회")
    @GetMapping("/weather")
    public ResponseEntity<List<LocationWeatherResponse>> getWeathersForAllLocations() {
        List<LocationWeatherResponse> responses = locationService.getWeathersForAllLocations();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "사용자 지역 좋아요")
    @PostMapping("/{locationId}/like")
    public ResponseEntity<Void> likeLocation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                             @Valid @PathVariable("locationId") Long locationId) {
        Long userId = customUserDetails.getUser().getId();
        locationLikeService.likeLocation(userId, locationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 지역 좋아요 취소")
    @DeleteMapping("/{locationId}/like")
    public ResponseEntity<Void> unlikeLocation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                               @Valid @PathVariable("locationId") Long locationId) {
        Long userId = customUserDetails.getUser().getId();
        locationLikeService.unlikeLocation(userId, locationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 지역 좋아요 여부 확인")
    @GetMapping("/{locationId}/like")
    public ResponseEntity<CheckLocationLikeResponse> checkLocationLike(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                       @Valid @PathVariable("locationId") Long locationId) {
        Long userId = customUserDetails.getUser().getId();
        CheckLocationLikeResponse response = locationLikeService.checkLocationLike(userId, locationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "단일 지역 좋아요 수 조회")
    @GetMapping("/{locationId}/like/count")
    public ResponseEntity<LocationLikeCountResponse> getLocationLikeCount(@Valid @PathVariable("locationId") Long locationId) {
        LocationLikeCountResponse response = locationLikeService.getLocationLikeCount(locationId);
        return ResponseEntity.ok(response);
    }

}
