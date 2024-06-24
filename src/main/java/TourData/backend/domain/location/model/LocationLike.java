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
@Table(name = "locationLike")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locationId")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    public LocationLike(Location location, User user) {
        setLocation(location);
        setUser(user);
    }

    public void setLocation(Location location){
        this.location = location;
        location.setLocationLike(this);
    }

    public void setUser(User user){
        this.user = user;
        user.setLocationLike(this);
    }

}