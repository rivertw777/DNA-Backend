package DNA_Backend.api_server.domain.location.dto.response;

public record LocationDetailResponse(Long locationId, String locationName, String thumbnail,
                                     double latitude, double longitude, double averageRating,
                                     int reviewCount) {
}