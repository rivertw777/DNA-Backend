package DNA_Backend.api_server.domain.location.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LocationWeatherApiResponse(
        LocationWeatherApiResponse.Main main,
        LocationWeatherApiResponse.Clouds clouds
) {
    public record Main(@JsonProperty("temp") double temp, @JsonProperty("humidity") double humidity) {}
    public record Clouds(@JsonProperty("all") double all) {}
}