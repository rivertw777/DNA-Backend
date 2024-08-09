package TourData.backend.domain.review.service;

import TourData.backend.domain.review.dto.ReviewDto.ReviewResponse;
import TourData.backend.domain.review.dto.ReviewDto.ReviewWriteRequest;
import TourData.backend.domain.review.model.entity.Review;
import TourData.backend.domain.review.repository.ReviewRepository;
import TourData.backend.domain.workationSchedule.model.entity.WorkationSchedule;
import TourData.backend.domain.workationSchedule.service.WorkationScheduleService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final WorkationScheduleService workationScheduleService;

    // 워케이션 일정 리뷰 작성
    @Transactional
    public void writeReview(ReviewWriteRequest reqeustParam) {
        WorkationSchedule workationSchedule = workationScheduleService.findWorkationSchedule(reqeustParam.scheduleId());
        saveReview(workationSchedule, reqeustParam);
    }

    private void saveReview(WorkationSchedule workationSchedule, ReviewWriteRequest reqeustParam){
        Review review = Review.createReview(workationSchedule, reqeustParam);
        reviewRepository.save(review);
    }

    // 사용자 전체 리뷰 조회
    @Transactional(readOnly = true)
    public List<ReviewResponse> getAllReviews(Long userId) {
        List<Review> reviews = reviewRepository.findByWorkationScheduleUserId(userId);

        return reviews.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private ReviewResponse toResponseDto(Review review) {
        WorkationSchedule workationSchedule = review.getWorkationSchedule();
        return new ReviewResponse(
                workationSchedule.getUser().getUsername(),
                workationSchedule.getLocationName(),
                workationSchedule.getStartDate(),
                workationSchedule.getEndDate(),
                review.getContent(),
                review.getCreatedAt());
    }

}
