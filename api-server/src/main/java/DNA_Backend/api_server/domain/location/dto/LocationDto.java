package DNA_Backend.api_server.domain.location.dto;

public class LocationDto {

    public record LocationResponse(Long locationId, String locationName, String thumbNail,
                                   double latitude, double longitude) {
    }

    public record LocationDetailResponse(Long locationId, String locationName, String thumbNail,
                                         double latitude, double longitude, float internetSpeed,
                                         float priceIndex, float populationDensity) {
    }

    public record CheckLocationLikeResponse(boolean isLiked) {
    }

    public record LocationLikeCountResponse(long likeCount) {
    }

}
