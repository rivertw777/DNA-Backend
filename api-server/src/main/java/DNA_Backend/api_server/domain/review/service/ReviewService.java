package DNA_Backend.api_server.domain.review.service;

import static DNA_Backend.api_server.domain.review.message.ReviewExceptionMessage.ALREADY_EXISTS;

import DNA_Backend.api_server.domain.location.model.entity.Location;
import DNA_Backend.api_server.domain.review.dto.ReviewDto.WriteReviewRequest;
import DNA_Backend.api_server.domain.review.dto.ReviewResponse;
import DNA_Backend.api_server.domain.review.model.entity.Review;
import DNA_Backend.api_server.domain.review.repository.ReviewRepository;
import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.user.service.UserService;
import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
import DNA_Backend.api_server.domain.workationSchedule.service.WorkationScheduleService;
import DNA_Backend.api_server.global.exception.DnaApplicationException;
import DNA_Backend.api_server.domain.review.utils.ReviewPage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final WorkationScheduleService workationScheduleService;
    private final UserService userService;

    // USER - 워케이션 리뷰 작성
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "LocationDetail", key= "#p3"),
            @CacheEvict(cacheNames = "AllWorkationReviews", allEntries = true),
            @CacheEvict(cacheNames = "LocationWorkationReviews", key= "#p3"),
    })
    public void writeReview(Long userId, Long scheduleId, WriteReviewRequest requestParam, Long locationId) {
        User user = userService.findUser(userId);
        WorkationSchedule workationSchedule = workationScheduleService.findWorkationSchedule(scheduleId);
        validateReviewNotExists(workationSchedule);
        saveReview(user, workationSchedule, requestParam);
        Location location = workationSchedule.getLocation();
        updateLocationAverageRating(location);
    }

    private void validateReviewNotExists(WorkationSchedule workationSchedule) {
        if (workationSchedule.getReview() != null) {
            throw new DnaApplicationException(ALREADY_EXISTS.getMessage());
        }
    }

    private void saveReview(User user, WorkationSchedule workationSchedule, WriteReviewRequest requestParam){
        Review review = Review.createReview(user, workationSchedule, requestParam.content(), requestParam.rating());
        reviewRepository.save(review);
    }

    // 지역 평점 갱신
    private void updateLocationAverageRating(Location location) {
        List<Review> reviews = reviewRepository.findByWorkationScheduleLocationId(location.getId());
        double averageRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
        location.setAverageRating(averageRating);
    }

    // USER - 전체 워케이션 리뷰 조회
    @Transactional(readOnly = true)
    public List<ReviewResponse> getUserReviews(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
    }

    // PUBLIC - 전체 워케이션 리뷰 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "AllWorkationReviews", keyGenerator = "ReviewPageKeyGenerator", cacheManager = "redisCacheManager")
    public ReviewPage<ReviewResponse> getAllReviews(Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findAll(pageable);
        return new ReviewPage<>(reviewPage.map(ReviewResponse::new));
    }

    // PUBLIC - 단일 지역 워케이션 리뷰 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "LocationWorkationReviews", keyGenerator = "ReviewPageKeyGenerator", cacheManager = "redisCacheManager")
    public ReviewPage<ReviewResponse> getLocationReviews(Pageable pageable, Long locationId) {
        Page<Review> reviewPage = reviewRepository.findByWorkationScheduleLocationId(locationId, pageable);
        return new ReviewPage<>(reviewPage.map(ReviewResponse::new));
    }

}
