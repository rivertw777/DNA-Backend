package DNA_Backend.api_server.domain.workationReview.model.entity;

import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
import DNA_Backend.api_server.common.model.entity.BaseTimeEntity;
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
@Table(name = "workation_reviews")
public class WorkationReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "rating")
    private int rating;

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

    public static WorkationReview createWorkationReview(User user, WorkationSchedule workationSchedule, String content, int rating) {
        WorkationReview workationReview = WorkationReview.builder()
                .rating(rating)
                .content(content)
                .user(user)
                .workationSchedule(workationSchedule)
                .build();
        user.addReview(workationReview);
        workationSchedule.setWorkationReview(workationReview);
        return workationReview;
    }

}
