package DNA_Backend.api_server.domain.location.dto.response;

public record LocationWeatherResponse(
        Long locationId,
        double temperature,
        double humidity,
        double cloudiness
) {
}
