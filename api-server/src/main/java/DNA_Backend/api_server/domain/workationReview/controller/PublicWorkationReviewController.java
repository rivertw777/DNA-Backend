package DNA_Backend.api_server.domain.workationReview.controller;

import DNA_Backend.api_server.domain.workationReview.dto.response.WorkationReviewResponse;
import DNA_Backend.api_server.domain.workationReview.service.WorkationReviewService;
import DNA_Backend.api_server.domain.workationReview.utils.WorkationReviewPage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/workation-reviews")
public class PublicWorkationReviewController {

    private final WorkationReviewService workationReviewService;

    @Operation(summary = "PUBLIC - 전체 워케이션 리뷰 조회")
    @GetMapping
    public ResponseEntity<WorkationReviewPage<WorkationReviewResponse>> getAllWorkationReviews(Pageable pageable) {
        WorkationReviewPage<WorkationReviewResponse> responses = workationReviewService.getAllWorkationReviews(pageable);
        return ResponseEntity.ok(responses);
    }

}
