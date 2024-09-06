package DNA_Backend.api_server.domain.review.controller;

import DNA_Backend.api_server.domain.review.dto.ReviewDto.ReviewResponse;
import DNA_Backend.api_server.domain.review.service.ReviewService;
import DNA_Backend.api_server.global.security.auth.UserDetailsCustom;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "USER - 전체 워케이션 리뷰 조회")
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviewsByUserId(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        Long userId = userDetailsCustom.getUser().getId();
        List<ReviewResponse> responses = reviewService.getAllReviewsByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "PUBLIC - 전체 워케이션 리뷰 조회")
    @GetMapping("/all")
    public ResponseEntity<Page<ReviewResponse>> getAllReviews(Pageable pageable) {
        Page<ReviewResponse> responses = reviewService.getAllReviews(pageable);
        return ResponseEntity.ok(responses);
    }

}
