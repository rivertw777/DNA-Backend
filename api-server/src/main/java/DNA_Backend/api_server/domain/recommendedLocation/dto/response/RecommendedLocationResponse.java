package DNA_Backend.api_server.domain.recommendedLocation.dto.response;

public record RecommendedLocationResponse(
        Long locationId,
        String locationName,
        String thumbnail,
        double latitude,
        double longitude,
        String keyword,
        String description
) {
}
