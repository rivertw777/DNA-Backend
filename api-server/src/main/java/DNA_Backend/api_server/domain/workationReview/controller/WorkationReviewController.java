package DNA_Backend.api_server.domain.workationReview.controller;

import DNA_Backend.api_server.domain.workationReview.dto.response.WorkationReviewResponse;
import DNA_Backend.api_server.domain.workationReview.service.WorkationReviewService;
import DNA_Backend.api_server.domain.workationReview.utils.WorkationReviewPage;
import DNA_Backend.api_server.global.security.auth.UserDetailsCustom;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorkationReviewController {

    private final WorkationReviewService workationReviewService;

    @Operation(summary = "USER - 전체 워케이션 리뷰 조회")
    @GetMapping("/api/workation-reviews")
    public ResponseEntity<List<WorkationReviewResponse>> getUserWorkationReviews(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        Long userId = userDetailsCustom.getUser().getId();
        List<WorkationReviewResponse> responses = workationReviewService.getUserWorkationReviews(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "PUBLIC - 전체 워케이션 리뷰 조회")
    @GetMapping("/api/public/workation-reviews/all")
    public ResponseEntity<WorkationReviewPage<WorkationReviewResponse>> getAllWorkationReviews(Pageable pageable) {
        WorkationReviewPage<WorkationReviewResponse> responses = workationReviewService.getAllWorkationReviews(pageable);
        return ResponseEntity.ok(responses);
    }

}