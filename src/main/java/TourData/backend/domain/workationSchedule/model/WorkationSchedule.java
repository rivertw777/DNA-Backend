package TourData.backend.domain.workationSchedule.model;

import TourData.backend.domain.location.model.Location;
import TourData.backend.domain.review.model.Review;
import TourData.backend.domain.workationSchedule.dto.WorkationScheduleDto.CreateWorkationScheduleRequest;
import TourData.backend.domain.user.model.User;
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
import java.time.LocalDateTime;
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
public class WorkationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(mappedBy = "workationSchedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Review review;

    @NotNull
    @Column(name = "is_expired")
    private boolean isExpired;

    public static WorkationSchedule createWorkationSchedule(CreateWorkationScheduleRequest requestParam, User user, Location location) {
        WorkationSchedule workationSchedule = WorkationSchedule.builder()
                .startDate(requestParam.startDate())
                .endDate(requestParam.endDate())
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