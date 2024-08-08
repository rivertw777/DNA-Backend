package TourData.backend.domain.facility.model.entity;

import TourData.backend.domain.facility.model.enums.FacilityType;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "facilities")
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
    @Column(name = "location_code")
    private String locationCode;

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

    public static Facility createFacility(String name, FacilityType type, String locationcode, String address,
                                          double latitude, double longitude) {
        return Facility.builder()
                .name(name)
                .type(type)
                .locationCode(locationcode)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public void addFacilityBookmarks(FacilityBookmark facilityBookmark) {
        this.facilityBookmarks.add(facilityBookmark);
    }

}
