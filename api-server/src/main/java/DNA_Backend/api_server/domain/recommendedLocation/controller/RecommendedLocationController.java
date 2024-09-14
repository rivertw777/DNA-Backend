package DNA_Backend.api_server.domain.recommendedLocation.controller;

import DNA_Backend.api_server.domain.recommendedLocation.dto.request.RecommendLocationRequest;
import DNA_Backend.api_server.domain.recommendedLocation.dto.response.RecommendLocationResponse;
import DNA_Backend.api_server.domain.recommendedLocation.dto.response.RecommendedLocationResponse;
import DNA_Backend.api_server.domain.recommendedLocation.service.RecommendedLocationService;
import DNA_Backend.api_server.global.security.auth.UserDetailsCustom;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendedLocationController {

    private final RecommendedLocationService recommendedLocationService;

    @Operation(summary = "USER - 지역 추천")
    @PostMapping
    public ResponseEntity<RecommendLocationResponse> recommendLocation(
            @AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
            @Valid @RequestBody RecommendLocationRequest requestParam) {
        Long userId = userDetailsCustom.getUser().getId();
        RecommendLocationResponse response = recommendedLocationService.recommendLocation(userId, requestParam);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "USER - 전체 추천 지역 조회")
    @GetMapping("/locations")
    public ResponseEntity<List<RecommendedLocationResponse>> getRecommendedLocations (
            @AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        Long userId = userDetailsCustom.getUser().getId();
        List<RecommendedLocationResponse> responses = recommendedLocationService.getRecommendedLocations(userId);
        return ResponseEntity.ok(responses);
    }

}