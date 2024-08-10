package TourData.backend.domain.review.model.entity;

import TourData.backend.domain.review.dto.ReviewDto.ReviewWriteRequest;
import TourData.backend.domain.user.model.entity.User;
import TourData.backend.domain.workationSchedule.model.entity.WorkationSchedule;
import TourData.backend.global.model.entity.BaseTimeEntity;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private WorkationSchedule workationSchedule;

    @NotNull
    @Column(name = "content")
    private String content;

    public static Review createReview(User user, WorkationSchedule workationSchedule, ReviewWriteRequest requestParam) {
        Review review = Review.builder()
                .user(user)
                .workationSchedule(workationSchedule)
                .content(requestParam.content())
                .build();
        user.addReview(review);
        workationSchedule.setReview(review);
        return review;
    }

}
