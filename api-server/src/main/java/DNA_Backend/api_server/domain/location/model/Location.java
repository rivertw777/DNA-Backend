package DNA_Backend.api_server.domain.location.model;

import DNA_Backend.api_server.domain.facility.model.Facility;
import DNA_Backend.api_server.domain.workationSchedule.model.WorkationSchedule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", unique = true, insertable = false, updatable = false)
    private LocationName name;

    @Column(name = "latitude", insertable = false, updatable = false)
    private double latitude;

    @Column(name = "longitude", insertable = false, updatable = false)
    private double longitude;

    @Column(name = "thumbnail", insertable = false, updatable = false)
    private String thumbnail;

    @OneToMany(mappedBy = "location")
    private List<LocationLike> locationLikes = new ArrayList<>();

    @OneToMany(mappedBy = "location")
    private List<Facility> facilities = new ArrayList<>();

    @OneToMany(mappedBy = "location")
    private List<WorkationSchedule> workationSchedules = new ArrayList<>();

    public void addLocationLike(LocationLike locationLike){
        this.locationLikes.add(locationLike);
    }

    public void addWorkationSchedule(WorkationSchedule workationSchedule) { this.workationSchedules.add(workationSchedule);
    }

}