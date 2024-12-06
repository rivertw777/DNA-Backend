package DNA_Backend.api_server.domain.workationOffice.model.entity;

import DNA_Backend.api_server.common.model.entity.BaseTimeEntity;
import DNA_Backend.api_server.domain.user.model.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "workation_office_bookmarks")
public class WorkationOfficeBookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workation_office_id")
    private WorkationOffice workationOffice;

    public static WorkationOfficeBookmark createWorkationOfficeBookmark(User user, WorkationOffice workationOffice) {
        WorkationOfficeBookmark workationOfficeBookmark = WorkationOfficeBookmark.builder()
                .user(user)
                .workationOffice(workationOffice)
                .build();
        user.addWorkationOfficeBookmark(workationOfficeBookmark);
        workationOffice.addWorkationOfficeBookmark(workationOfficeBookmark);
        return workationOfficeBookmark;
    }

}
