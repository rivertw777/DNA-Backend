package TourData.backend.domain.facility.model;

import jakarta.persistence.CascadeType;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "facilities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private FacilityType type;

    @NotNull
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "latitude")
    private double latitude;

    @NotNull
    @Column(name = "longitude")
    private double longitude;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    private List<FacilityBookmark> facilityBookmarks = new ArrayList<>();

    @Builder
    public Facility(String name, FacilityType type, String address, double latitude, double longitude) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void addFacilityBookmarks(FacilityBookmark facilityBookmark) {
        this.facilityBookmarks.add(facilityBookmark);
    }

}