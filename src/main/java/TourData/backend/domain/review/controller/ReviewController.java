package TourData.backend.domain.review.controller;

import TourData.backend.domain.review.dto.ReviewDto.ReviewResponse;
import TourData.backend.domain.review.dto.ReviewDto.ReviewWriteRequest;
import TourData.backend.domain.review.service.ReviewService;
import TourData.backend.global.security.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "워케이션 일정 리뷰 작성")
    @PostMapping
    public ResponseEntity<Void> writeReview(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @Valid @RequestBody ReviewWriteRequest reqeustParam) {
        Long userId = customUserDetails.getUser().getId();
        reviewService.writeReview(userId, reqeustParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 전체 리뷰 조회")
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUser().getId();
        List<ReviewResponse> responses = reviewService.getAllReviews(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "지역 전체 리뷰 페이지 조회")
    @GetMapping("/location")
    public ResponseEntity<Page<ReviewResponse>> getLocationReviews(@RequestParam("locationName") String locationName){
        return null;
    }

}
