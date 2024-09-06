package DNA_Backend.api_server.domain.recommendation.dto.response;

import DNA_Backend.api_server.domain.recommendation.model.entity.RecommendedLocation;

public record RecommendedLocationResponse(Long locationId, String locationName, String thumbnail) {
    public RecommendedLocationResponse(RecommendedLocation recommendedLocation){
        this(
                recommendedLocation.getLocation().getId(),
                recommendedLocation.getLocation().getName().getValue(),
                recommendedLocation.getLocation().getThumbnail()
        );
    }
}
