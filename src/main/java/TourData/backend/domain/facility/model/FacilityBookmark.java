package TourData.backend.domain.facility.model;

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

@Getter
@Entity
@Table(name = "facility_bookmarks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FacilityBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @Builder
    public FacilityBookmark(User user, Facility facility) {
        setUser(user);
        setFacility(facility);
    }

    public void setUser(User user) {
        this.user = user;
        user.addFacilityBookmark(this);
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
        facility.addFacilityBookmarks(this);
    }

}