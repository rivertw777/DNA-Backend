package DNA_Backend.api_server.domain.user.model.entity;

import DNA_Backend.api_server.domain.facility.model.entity.FacilityBookmark;
import DNA_Backend.api_server.domain.recommendation.model.entity.RecommendedLocation;
import DNA_Backend.api_server.domain.review.model.entity.Review;
import DNA_Backend.api_server.domain.user.model.enums.PopupStatus;
import DNA_Backend.api_server.domain.user.model.enums.Role;
import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
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
import java.util.Collections;
import java.util.List;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RecommendedLocation> recommendedLocations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FacilityBookmark> facilityBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WorkationSchedule> workationSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "popup_status")
    private PopupStatus popupStatus;

    // 일반 회원 가입
    public static User createUser(String username, String email, String encodedPassword) {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .roles(Collections.singletonList(Role.USER))
                .popupStatus(PopupStatus.NONE)
                .build();
        return user;
    }

    // 소셜 계정 회원 가입
    public static User createUser(String username, String email, String provider, String providerId) {
        User user = User.builder()
                .username(username)
                .email(email)
                .roles(Collections.singletonList(Role.USER))
                .provider(provider)
                .providerId(providerId)
                .popupStatus(PopupStatus.NONE)
                .build();
        return user;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setPopupStatus(PopupStatus popupStatus) {
        this.popupStatus = popupStatus;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

}