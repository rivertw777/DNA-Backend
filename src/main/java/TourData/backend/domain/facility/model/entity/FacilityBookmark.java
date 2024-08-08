package TourData.backend.domain.facility.model.entity;

import TourData.backend.domain.user.model.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "facility_bookmarks")
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

    public static FacilityBookmark createFacilityBookmark(User user, Facility facility) {
        FacilityBookmark facilityBookmark = FacilityBookmark.builder()
                .user(user)
                .facility(facility)
                .build();
        user.addFacilityBookmark(facilityBookmark);
        facility.addFacilityBookmarks(facilityBookmark);
        return facilityBookmark;
    }

}