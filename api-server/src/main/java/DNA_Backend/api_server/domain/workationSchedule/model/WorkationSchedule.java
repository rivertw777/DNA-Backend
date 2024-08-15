package DNA_Backend.api_server.domain.workationSchedule.model;

import DNA_Backend.api_server.domain.location.model.Location;
import DNA_Backend.api_server.domain.review.model.Review;
import DNA_Backend.api_server.domain.user.model.User;
import DNA_Backend.api_server.global.model.BaseTimeEntity;
import jakarta.persistence.CascadeType;
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
import java.time.LocalDate;
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
@Table(name = "workation_schedules")
public class WorkationSchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    // 수정 예정
    @OneToOne(mappedBy = "workationSchedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Review review;

    @NotNull
    @Column(name = "is_expired")
    private boolean isExpired;

    public static WorkationSchedule createWorkationSchedule(User user, Location location, LocalDate startDate, LocalDate endDate) {
        WorkationSchedule workationSchedule = WorkationSchedule.builder()
                .startDate(startDate)
                .endDate(endDate)
                .user(user)
                .location(location)
                .isExpired(false)
                .build();
        user.addSchedule(workationSchedule);
        location.addWorkationSchedule(workationSchedule);
        return workationSchedule;
    }

    public void setIsExpired(boolean isExpired){
        this.isExpired = isExpired;
    }

    public void setReview(Review review){
        this.review = review;
    }

}