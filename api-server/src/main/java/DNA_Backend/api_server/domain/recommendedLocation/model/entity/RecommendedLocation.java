package DNA_Backend.api_server.domain.recommendedLocation.model.entity;

import DNA_Backend.api_server.domain.location.model.entity.Location;
import DNA_Backend.api_server.domain.user.model.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "recommended_locations")
public class RecommendedLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ranking")
    private int ranking;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    public static RecommendedLocation createRecommendedLocation(User user, Location location, int ranking) {
        RecommendedLocation recommendedLocation = RecommendedLocation.builder()
                .user(user)
                .location(location)
                .ranking(ranking)
                .build();
        user.addRecommendedLocation(recommendedLocation);
        location.addRecommendedLocation(recommendedLocation);
        return recommendedLocation;
    }

}