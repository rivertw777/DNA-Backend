package DNA_Backend.api_server.domain.location.dto.response;

public record LocationResponse(Long locationId, String locationName, String thumbnail,
                               double latitude, double longitude, String keyword, String description) {
}