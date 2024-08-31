package DNA_Backend.api_server.domain.recommendation.dto;

import java.util.List;

public class RecommendationDto {

    public record RecommendLocationRequest(
            int activities,
            int visitTypes,
            int incomeRanges,
            int preferenceTypes,
            int tripCount,
            int accommodationPreference,
            int workationGoals,
            int relaxationExperience,
            int photoImportance
    ) {}

    public record RecommendLocationResponse(List<String> locationNames){
    }

    public record RecommendedLocationResponse(Long locationId, String locationName, String thumbnail) {
    }

}
