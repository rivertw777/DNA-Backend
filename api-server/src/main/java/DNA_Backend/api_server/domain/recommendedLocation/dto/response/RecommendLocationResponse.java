package DNA_Backend.api_server.domain.recommendedLocation.dto.response;

import java.util.List;

public record RecommendLocationResponse(List<LocationData> locations) {
}