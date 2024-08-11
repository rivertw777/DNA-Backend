package TourData.backend.domain.review.model;

import TourData.backend.domain.review.dto.ReviewDto.WriteReviewRequest;
import TourData.backend.domain.user.model.User;
import TourData.backend.domain.workationSchedule.model.WorkationSchedule;
import TourData.backend.global.model.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "reviews")
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "content")
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workation_schedule_id")
    private WorkationSchedule workationSchedule;

    public static Review createReview(WriteReviewRequest requestParam, User user, WorkationSchedule workationSchedule) {
        Review review = Review.builder()
                .content(requestParam.content())
                .user(user)
                .workationSchedule(workationSchedule)
                .build();
        user.addReview(review);
        workationSchedule.setReview(review);
        return review;
    }

}
