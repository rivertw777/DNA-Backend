package TourData.backend.domain.user.model.entity;

import TourData.backend.domain.facility.model.entity.FacilityBookmark;
import TourData.backend.domain.location.model.entity.LocationLike;
import TourData.backend.domain.workationSchedule.model.entity.WorkationSchedule;
import TourData.backend.domain.user.dto.UserDto.UserSignUpRequest;
import TourData.backend.domain.user.model.enums.Role;
import TourData.backend.global.security.oauth.provider.OAuth2UserInfo;
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
    private List<LocationLike> locationLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FacilityBookmark> facilityBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WorkationSchedule> workationSchedules = new ArrayList<>();

    @Column(name = "has_received_email")
    private boolean hasReceivedEmail;

    // 일반 회원 가입
    public static User createUser(UserSignUpRequest requestParam, String encodedPassword) {
        User user = User.builder()
                .username(requestParam.username())
                .password(encodedPassword)
                .email(requestParam.email())
                .roles(Collections.singletonList(Role.USER))
                .build();
        return user;
    }

    // 소셜 계정 회원 가입
    public static User createUser(String username, OAuth2UserInfo oAuth2UserInfo) {
        User user = User.builder()
                .username(username)
                .email(oAuth2UserInfo.getEmail())
                .roles(Collections.singletonList(Role.USER))
                .provider(oAuth2UserInfo.getProvider())
                .providerId(oAuth2UserInfo.getProviderId())
                .build();
        return user;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addLocationLike(LocationLike locationLike){
        this.locationLikes.add(locationLike);
    }

    public void addFacilityBookmark(FacilityBookmark facilityBookmark){
        this.facilityBookmarks.add(facilityBookmark);
    }

    public void addSchedule(WorkationSchedule workationSchedule){
        this.workationSchedules.add(workationSchedule);
    }

}