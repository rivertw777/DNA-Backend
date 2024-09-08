package DNA_Backend.api_server.domain.location.dto.response;

public record LocationDetailResponse(Long locationId, String locationName, String thumbnail,
                                     double latitude, double longitude, float internetSpeed,
                                     float priceIndex, float populationDensity, double averageRating,
                                     int reviewCount) {
}