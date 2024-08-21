package DNA_Backend.api_server.domain.recommendation.dto;

public class RecommendationDto {

    public record RecommendLocationRequest(int input){
    }

    public record RecommendLocationResponse(String output){
    }

}
