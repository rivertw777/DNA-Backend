package DNA_Backend.api_server.domain.recommendedLocation.dto.request;

public record RecommendLocationRequest(
        int gender,
        int age,
        int income,
        int travelCompanions,
        int travelPreference,
        int newOrFamiliar,
        int comfortVsCost,
        int relaxationVsActivities,
        int knownVsUnknown,
        int photographyImportance
) {}
