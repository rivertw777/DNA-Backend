package DNA_Backend.api_server.domain.workationReview.service;

import DNA_Backend.api_server.domain.location.model.entity.Location;
import DNA_Backend.api_server.domain.workationReview.model.entity.WorkationReview;
import DNA_Backend.api_server.domain.workationReview.repository.WorkationReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkationScheduleReviewService {

    private final WorkationReviewRepository workationReviewRepository;

    // 지역 평점, 리뷰 수 갱신
    public void updateLocationData(Location location) {
        List<WorkationReview> workationReviews = workationReviewRepository.findByWorkationScheduleLocationId(location.getId());
        double averageRating = workationReviews.stream()
                .mapToInt(WorkationReview::getRating)
                .average()
                .orElse(0.0);
        int reviewCount = workationReviews.size();
        location.updateData(averageRating, reviewCount);
    }

}