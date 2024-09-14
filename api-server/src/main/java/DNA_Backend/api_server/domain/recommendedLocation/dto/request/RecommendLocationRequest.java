package DNA_Backend.api_server.domain.recommendedLocation.dto.request;

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