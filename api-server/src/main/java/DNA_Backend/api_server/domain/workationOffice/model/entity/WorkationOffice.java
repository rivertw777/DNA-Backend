package DNA_Backend.api_server.domain.workationOffice.model.entity;

import DNA_Backend.api_server.domain.location.model.entity.Location;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Entity
@Table(name = "workation_offices")
public class WorkationOffice {

    @Id
    private Long id;

    @Column(name = "name", insertable = false, updatable = false)
    private String name;

    @Column(name = "address", insertable = false, updatable = false)
    private String address;

    @Column(name = "latitude", insertable = false, updatable = false)
    private double latitude;

    @Column(name = "longitude", insertable = false, updatable = false)
    private double longitude;

    @Column(name = "open_time", insertable = false, updatable = false)
    private String businessHours;

    @Column(name = "tel_number", insertable = false, updatable = false)
    private String telNumber;

    @Column(name = "level", insertable = false, updatable = false)
    private int level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", insertable = false, updatable = false)
    private Location location;

    @OneToMany(mappedBy = "workationOffice", cascade = CascadeType.ALL)
    private List<WorkationOfficeBookmark> workationOfficeBookmarks = new ArrayList<>();

    public void addWorkationOfficeBookmark(WorkationOfficeBookmark workationOfficeBookmark) {
        this.workationOfficeBookmarks.add(workationOfficeBookmark);
    }

}
