package DNA_Backend.api_server.domain.recommendation.controller;

import DNA_Backend.api_server.domain.recommendation.dto.RecommendationDto.RecommendLocationRequest;
import DNA_Backend.api_server.domain.recommendation.dto.RecommendationDto.RecommendLocationResponse;
import DNA_Backend.api_server.domain.recommendation.dto.RecommendationDto.RecommendedLocationResponse;
import DNA_Backend.api_server.domain.recommendation.service.RecommendationService;
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
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Operation(summary = "사용자 지역 추천")
    @PostMapping
    public ResponseEntity<RecommendLocationResponse> recommendLocation(
            @AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
            @Valid @RequestBody RecommendLocationRequest requestParam) {
        Long userId = userDetailsCustom.getUser().getId();
        RecommendLocationResponse response = recommendationService.recommendLocation(userId, requestParam);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자 추천 지역 조회")
    @GetMapping("/locations")
    public ResponseEntity<List<RecommendedLocationResponse>> getRecommendedLocations (
            @AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        Long userId = userDetailsCustom.getUser().getId();
        List<RecommendedLocationResponse> responses = recommendationService.getRecommendedLocations(userId);
        return ResponseEntity.ok(responses);
    }

}