package TourData.backend.domain.workationSchedule.model.entity;

import TourData.backend.domain.workationSchedule.dto.WorkationScheduleDto.WorkationScheduleCreateRequest;
import TourData.backend.domain.user.model.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @Column(name = "location_name")
    private String locationName;

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

    public static WorkationSchedule createWorkationSchedule(User user, WorkationScheduleCreateRequest requestParam) {
        WorkationSchedule workationSchedule = WorkationSchedule.builder()
                .user(user)
                .locationName(requestParam.LocationName())
                .startDate(requestParam.startDate())
                .endDate(requestParam.endDate())
                .build();
        user.addSchedule(workationSchedule);
        return workationSchedule;
    }

}