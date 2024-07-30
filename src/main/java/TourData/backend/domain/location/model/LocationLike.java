package TourData.backend.domain.location.model;

import TourData.backend.domain.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Builder
    public LocationLike(Location location, User user) {
        setLocation(location);
        setUser(user);
    }

    public void setLocation(Location location){
        this.location = location;
        location.addLocationLike(this);
    }

    public void setUser(User user){
        this.user = user;
        user.addLocationLike(this);
    }

}