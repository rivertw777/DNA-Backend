package DNA_Backend.api_server.domain.location.dto;

public class LocationDto {

    public record LocationResponse(Long locationId, String locationName, String thumbNail) {
    }

    public record CheckLocationLikeResponse(boolean isLiked) {
    }

    public record LocationLikeCountResponse(long likeCount) {
    }

}
