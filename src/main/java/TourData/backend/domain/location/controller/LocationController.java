package TourData.backend.domain.location.controller;

import TourData.backend.domain.location.dto.LocationDto.LocationLikeCheckResponse;
import TourData.backend.domain.location.dto.LocationDto.LocationResponse;
import TourData.backend.domain.location.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

    private final LocationService locationSerivce;

    // 지역 정보 조회
    @Operation(summary = "지역 정보 조회")
    @GetMapping
    public List<LocationResponse> getAllLocations(){
        return locationSerivce.getAllLocations();
    }

    // 지역 좋아요
    @Operation(summary = "지역 좋아요")
    @PostMapping("/{locationId}/like")
    public void likeLocation(@AuthenticationPrincipal(expression = "username") String username,
                         @Valid @PathVariable("locationId") Long locationId) {
        locationSerivce.likeLocation(username, locationId);
    }

    @Operation(summary = "지역 좋아요 취소")
    @DeleteMapping("/{locationId}/like")
    public void unlikeLocation(@AuthenticationPrincipal(expression = "username") String username,
                           @Valid @PathVariable("locationId") Long locationId) {
        locationSerivce.unlikeLocation(username, locationId);
    }

    @Operation(summary = "지역 좋아요 여부 확인")
    @GetMapping("/{locationId}/like")
    public LocationLikeCheckResponse checkLocationLike(@AuthenticationPrincipal(expression = "username") String username,
                                               @Valid @PathVariable("locationId") Long locationId) {
        return locationSerivce.checkLocationLike(username, locationId);
    }

}
