package DNA_Backend.api_server.domain.user.model.entity;

import DNA_Backend.api_server.domain.facility.model.entity.FacilityBookmark;
import DNA_Backend.api_server.domain.recommendedLocation.model.entity.RecommendedLocation;
import DNA_Backend.api_server.domain.workationReview.model.entity.WorkationReview;
import DNA_Backend.api_server.domain.user.model.enums.PopupStatus;
import DNA_Backend.api_server.domain.user.model.enums.Role;
import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties({"provider", "providerId", "popupStatus", "recommendedLocations", "facilityBookmarks",
        "workationSchedules", "workationReviews"})
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, name = "username")
    private String username;

    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "password", length = 60)
    private String password;

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "popup_status")
    private PopupStatus popupStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RecommendedLocation> recommendedLocations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FacilityBookmark> facilityBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WorkationSchedule> workationSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WorkationReview> workationReviews;

    // 일반 회원 가입
    public static User createUser(String username, String email, String encodedPassword) {
        List<Role> roles = new ArrayList<>(List.of(Role.USER));
        User user = User.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .roles(roles)
                .popupStatus(PopupStatus.NONE)
                .build();
        return user;
    }

    // 소셜 계정 회원 가입
    public static User createUser(String username, String email, String provider, String providerId) {
        List<Role> roles = new ArrayList<>(List.of(Role.USER));
        User user = User.builder()
                .username(username)
                .email(email)
                .roles(roles)
                .provider(provider)
                .providerId(providerId)
                .popupStatus(PopupStatus.NONE)
                .build();
        return user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPopupStatus(PopupStatus popupStatus) {
        this.popupStatus = popupStatus;
    }

    public void addRecommendedLocation(RecommendedLocation recommendedLocation){
        this.recommendedLocations.add(recommendedLocation);
    }

    public void addFacilityBookmark(FacilityBookmark facilityBookmark){
        this.facilityBookmarks.add(facilityBookmark);
    }

    public void addSchedule(WorkationSchedule workationSchedule){
        this.workationSchedules.add(workationSchedule);
    }

    public void addReview(WorkationReview workationReview) {
        this.workationReviews.add(workationReview);
    }

}