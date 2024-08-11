package TourData.backend.domain.location.model;

import TourData.backend.domain.facility.model.Facility;
import TourData.backend.domain.workationSchedule.model.WorkationSchedule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "name", unique = true)
    private LocationName name;

    @NotNull
    @Column(name = "latitude")
    private double latitude;

    @NotNull
    @Column(name = "longitude")
    private double longitude;

    @NotNull
    @Column(name = "thumbnail")
    private String thumbnail;

    @OneToMany(mappedBy = "location")
    private List<LocationLike> locationLikes = new ArrayList<>();

    @OneToMany(mappedBy = "location")
    private List<Facility> facilities = new ArrayList<>();

    @OneToMany(mappedBy = "location")
    private List<WorkationSchedule> workationSchedules = new ArrayList<>();

    public static Location createLocation(LocationName name, double latitude, double longitude, String thumbnail) {
        return Location.builder()
                .name(name)
                .latitude(latitude)
                .longitude(longitude)
                .thumbnail(thumbnail)
                .build();
    }

    public void addLocationLike(LocationLike locationLike){
        this.locationLikes.add(locationLike);
    }

    public void addFacility(Facility facility) {
        this.facilities.add(facility);
    }

    public void addWorkationSchedule(WorkationSchedule workationSchedule) { this.workationSchedules.add(workationSchedule);
    }

}