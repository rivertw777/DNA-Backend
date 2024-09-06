package DNA_Backend.api_server.domain.recommendation.dto.request;

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