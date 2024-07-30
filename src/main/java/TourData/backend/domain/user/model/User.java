package TourData.backend.domain.user.model;

import TourData.backend.domain.facility.model.FacilityBookmark;
import TourData.backend.domain.location.model.LocationLike;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public User(String username, String password, List<Role> roles, String email, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
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

}