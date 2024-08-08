package TourData.backend.domain.location.model.entity;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, name = "name")
    private String name;

    @NotNull
    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "latitude")
    private double latitude;

    @NotNull
    @Column(name = "longitude")
    private double longitude;

    @NotNull
    @Column(name = "thumbnail")
    private String thumbnail;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<LocationLike> locationLikes = new ArrayList<>();

    public static Location createLocation(String name, String code, double latitude, double longitude, String thumbnail) {
        return Location.builder()
                .name(name)
                .code(code)
                .latitude(latitude)
                .longitude(longitude)
                .thumbnail(thumbnail)
                .build();
    }

    public void addLocationLike(LocationLike locationLike){
        this.locationLikes.add(locationLike);
    }

}