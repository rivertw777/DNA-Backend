package DNA_Backend.api_server.domain.workationReview.service;

import static DNA_Backend.api_server.domain.workationReview.exception.WorkationReviewExceptionMessage.ALREADY_EXISTS;

import DNA_Backend.api_server.domain.location.model.entity.Location;
import DNA_Backend.api_server.domain.workationReview.dto.mapper.WorkationReviewMapper;
import DNA_Backend.api_server.domain.workationReview.dto.response.WorkationReviewResponse;
import DNA_Backend.api_server.domain.workationReview.model.entity.WorkationReview;
import DNA_Backend.api_server.domain.workationReview.repository.WorkationReviewRepository;
import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.user.service.UserService;
import DNA_Backend.api_server.domain.workationSchedule.dto.request.WriteWorkationReviewRequest;
import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
import DNA_Backend.api_server.domain.workationSchedule.service.WorkationScheduleService;
import DNA_Backend.api_server.common.exception.DnaApplicationException;
import DNA_Backend.api_server.domain.workationReview.utils.WorkationReviewPage;
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
    private final UserService userService;
    private final WorkationScheduleService workationScheduleService;
    private final LocationReviewService locationReviewService;
    private final WorkationReviewMapper workationReviewMapper;

    // USER - 워케이션 리뷰 작성
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "LocationDetail", key= "#p3"),
            @CacheEvict(cacheNames = "AllLocationWorkationReviews", allEntries = true),
            @CacheEvict(cacheNames = "LocationWorkationReviews", key= "#p3"),
    })
    public void writeWorkationReview(Long userId, Long scheduleId, WriteWorkationReviewRequest requestParam, Long locationId) {
        User user = userService.findUser(userId);
        WorkationSchedule workationSchedule = workationScheduleService.findWorkationSchedule(scheduleId);
        validateReviewNotExists(workationSchedule);
        saveWorkationReview(user, workationSchedule, requestParam);
        Location location = workationSchedule.getLocation();
        locationReviewService.updateReviewData(location);
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

    // PUBLIC - 전체 지역 워케이션 리뷰 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "AllLocationWorkationReviews", keyGenerator = "WorkationReviewPageKeyGenerator", cacheManager = "redisCacheManager")
    public WorkationReviewPage<WorkationReviewResponse> getAllLocationWorkationReviews(Pageable pageable) {
        Page<WorkationReview> reviewPage = workationReviewRepository.findAllWithFetchJoin(pageable);
        return new WorkationReviewPage<>(reviewPage.map(workationReviewMapper::toResponse));
    }

    // PUBLIC - 단일 지역 워케이션 리뷰 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "LocationWorkationReviews", keyGenerator = "WorkationReviewPageKeyGenerator", cacheManager = "redisCacheManager")
    public WorkationReviewPage<WorkationReviewResponse> getLocationWorkationReviews(Pageable pageable, Long locationId) {
        Page<WorkationReview> reviewPage = workationReviewRepository.findByWorkationScheduleLocationIdWithFetchJoin(locationId, pageable);
        return new WorkationReviewPage<>(reviewPage.map(workationReviewMapper::toResponse));
    }

}
