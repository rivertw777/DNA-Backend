package TourData.backend.domain.location.dto;

public class LocationDto {
    public record LocationResponse(Long id, String name, String thumbNail) {
    }

    public record LocationLikeCheckResponse (boolean isLike) {
    }

    public record LocationLikeCountResponse (Integer likeCount) {
    }
}
