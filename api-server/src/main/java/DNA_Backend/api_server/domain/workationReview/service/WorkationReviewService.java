package DNA_Backend.api_server.domain.workationReview.service;

import static DNA_Backend.api_server.domain.workationReview.message.WorkationReviewExceptionMessage.ALREADY_EXISTS;

import DNA_Backend.api_server.domain.location.model.entity.Location;
import DNA_Backend.api_server.domain.workationReview.dto.WorkationReviewDto.WriteWorkationReviewRequest;
import DNA_Backend.api_server.domain.workationReview.dto.WorkationReviewResponse;
import DNA_Backend.api_server.domain.workationReview.model.entity.WorkationReview;
import DNA_Backend.api_server.domain.workationReview.repository.WorkationReviewRepository;
import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.user.service.UserService;
import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
import DNA_Backend.api_server.domain.workationSchedule.service.WorkationScheduleService;
import DNA_Backend.api_server.global.exception.DnaApplicationException;
import DNA_Backend.api_server.domain.workationReview.utils.WorkationReviewPage;
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
public class WorkationReviewService {

    private final WorkationReviewRepository workationReviewRepository;
    private final WorkationScheduleService workationScheduleService;
    private final UserService userService;

    // USER - 워케이션 리뷰 작성
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "LocationDetail", key= "#p3"),
            @CacheEvict(cacheNames = "AllWorkationReviews", allEntries = true),
            @CacheEvict(cacheNames = "LocationWorkationReviews", key= "#p3"),
    })
    public void writeWorkationReview(Long userId, Long scheduleId, WriteWorkationReviewRequest requestParam, Long locationId) {
        User user = userService.findUser(userId);
        WorkationSchedule workationSchedule = workationScheduleService.findWorkationSchedule(scheduleId);
        validateReviewNotExists(workationSchedule);
        saveWorkationReview(user, workationSchedule, requestParam);
        Location location = workationSchedule.getLocation();
        updateLocationAverageRating(location);
    }

    private void validateReviewNotExists(WorkationSchedule workationSchedule) {
        if (workationSchedule.getWorkationReview() != null) {
            throw new DnaApplicationException(ALREADY_EXISTS.getValue());
        }
    }

    private void saveWorkationReview(User user, WorkationSchedule workationSchedule, WriteWorkationReviewRequest requestParam){
        WorkationReview workationReview = WorkationReview.createWorkationReview(user, workationSchedule, requestParam.content(), requestParam.rating());
        workationReviewRepository.save(workationReview);
    }

    // 지역 평점 갱신
    private void updateLocationAverageRating(Location location) {
        List<WorkationReview> workationReviews = workationReviewRepository.findByWorkationScheduleLocationId(location.getId());
        double averageRating = workationReviews.stream()
                .mapToInt(WorkationReview::getRating)
                .average()
                .orElse(0.0);
        location.setAverageRating(averageRating);
    }

    // USER - 전체 워케이션 리뷰 조회
    @Transactional(readOnly = true)
    public List<WorkationReviewResponse> getUserWorkationReviews(Long userId) {
        List<WorkationReview> workationReviews = workationReviewRepository.findByUserId(userId);
        return workationReviews.stream()
                .map(WorkationReviewResponse::new)
                .collect(Collectors.toList());
    }

    // PUBLIC - 전체 워케이션 리뷰 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "AllWorkationReviews", keyGenerator = "WorkationReviewPageKeyGenerator", cacheManager = "redisCacheManager")
    public WorkationReviewPage<WorkationReviewResponse> getAllWorkationReviews(Pageable pageable) {
        Page<WorkationReview> reviewPage = workationReviewRepository.findAll(pageable);
        return new WorkationReviewPage<>(reviewPage.map(WorkationReviewResponse::new));
    }

    // PUBLIC - 단일 지역 워케이션 리뷰 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "LocationWorkationReviews", keyGenerator = "WorkationReviewPageKeyGenerator", cacheManager = "redisCacheManager")
    public WorkationReviewPage<WorkationReviewResponse> getLocationWorkationReviews(Pageable pageable, Long locationId) {
        Page<WorkationReview> reviewPage = workationReviewRepository.findByWorkationScheduleLocationId(locationId, pageable);
        return new WorkationReviewPage<>(reviewPage.map(WorkationReviewResponse::new));
    }

}
