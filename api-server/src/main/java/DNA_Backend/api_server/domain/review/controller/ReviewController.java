package DNA_Backend.api_server.domain.review.controller;

import DNA_Backend.api_server.domain.review.dto.ReviewDto.WriteReviewRequest;
import DNA_Backend.api_server.domain.review.dto.ReviewDto.ReviewResponse;
import DNA_Backend.api_server.domain.review.service.ReviewService;
import DNA_Backend.api_server.global.security.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "사용자 워케이션 리뷰 작성")
    @PostMapping("/workation-schedules/{scheduleId}/reviews")
    public ResponseEntity<Void> writeReview(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @Valid @PathVariable("scheduleId") Long scheduleId,
                                            @Valid @RequestBody WriteReviewRequest reqeustParam) {
        Long userId = customUserDetails.getUser().getId();
        reviewService.writeReview(userId, scheduleId, reqeustParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 전체 워케이션 리뷰 조회")
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsByUserId(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUser().getId();
        List<ReviewResponse> responses = reviewService.getAllReviewsByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "전체 워케이션 리뷰 조회")
    @GetMapping("/reviews/all")
    public ResponseEntity<Page<ReviewResponse>> getAllReviews(Pageable pageable) {
        Page<ReviewResponse> responses = reviewService.getAllReviews(pageable);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "단일 지역 워케이션 리뷰 조회")
    @GetMapping("/locations/{locationId}/reviews")
    public ResponseEntity<Page<ReviewResponse>> getReviewsForLocation(
            @Valid @PathVariable(name = "locationId") Long locationId, Pageable pageable) {
        Page<ReviewResponse> responses = reviewService.getReviewsForLocation(locationId, pageable);
        return ResponseEntity.ok(responses);
    }

}
