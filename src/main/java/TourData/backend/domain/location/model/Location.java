package TourData.backend.domain.location.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "locations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, name = "name")
    private String name;

    @NotNull
    @Column(name = "latitude")
    private double latitude;

    @NotNull
    @Column(name = "longitude")
    private double longitude;

    @NotNull
    @Column(name = "thumbNail")
    private String thumbNail;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<LocationLike> locationLikes = new ArrayList<>();

    @Builder
    public Location(String name, double latitude, double longitude, String thumbNail) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.thumbNail = thumbNail;
    }

    public void setLocationLike(LocationLike locationLike){
        this.locationLikes.add(locationLike);
    }

}