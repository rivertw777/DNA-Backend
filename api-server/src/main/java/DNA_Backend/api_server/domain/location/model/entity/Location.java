package DNA_Backend.api_server.domain.location.model.entity;

import DNA_Backend.api_server.domain.facility.model.entity.Facility;
import DNA_Backend.api_server.domain.location.model.enums.LocationName;
import DNA_Backend.api_server.domain.recommendation.model.entity.RecommendedLocation;
import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
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

    @Column(name = "internet_speed", insertable = false, updatable = false)
    private float internetSpeed;

    @Column(name = "price_index", insertable = false, updatable = false)
    private float priceIndex;

    @Column(name = "population_density", insertable = false, updatable = false)
    private float populationDensity;

    @OneToMany(mappedBy = "location")
    private List<RecommendedLocation> recommendedLocations = new ArrayList<>();

    @OneToMany(mappedBy = "location")
    private List<Facility> facilities = new ArrayList<>();

    @OneToMany(mappedBy = "location")
    private List<WorkationSchedule> workationSchedules = new ArrayList<>();

    public void addRecommendedLocation(RecommendedLocation recommendedLocation){
        this.recommendedLocations.add(recommendedLocation);
    }

    public void addWorkationSchedule(WorkationSchedule workationSchedule) { this.workationSchedules.add(workationSchedule);
    }

}