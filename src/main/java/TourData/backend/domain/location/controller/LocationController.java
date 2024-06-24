package TourData.backend.domain.location.controller;

import TourData.backend.domain.location.dto.LocationDto.LocationLikeCheckResponse;
import TourData.backend.domain.location.dto.LocationDto.LocationResponse;
import TourData.backend.domain.location.service.LocationService;
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

    // 게시물 조회
    @GetMapping
    public List<LocationResponse> getAllPosts(){
        return locationSerivce.getAllLocations();
    }

    // 지역 좋아요
    @PostMapping("/{locationId}/like")
    public void likePost(@AuthenticationPrincipal(expression = "username") String username,
                         @Valid @PathVariable("locationId") Long locationId) {
        locationSerivce.likeLocation(username, locationId);
    }

    // 지역 좋아요 취소
    @DeleteMapping("/{locationId}/like")
    public void unlikePost(@AuthenticationPrincipal(expression = "username") String username,
                           @Valid @PathVariable("locationId") Long locationId) {
        locationSerivce.unlikeLocation(username, locationId);
    }

    // 지역 좋아요 여부 확인
    @GetMapping("/{locationId}/like")
    public LocationLikeCheckResponse checkLike(@AuthenticationPrincipal(expression = "username") String username,
                                               @Valid @PathVariable("locationId") Long locationId) {
        return locationSerivce.checkLocationLike(username, locationId);
    }

}
