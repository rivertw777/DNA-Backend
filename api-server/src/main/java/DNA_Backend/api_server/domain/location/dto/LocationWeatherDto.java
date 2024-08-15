package DNA_Backend.api_server.domain.location.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationWeatherDto {

    public record LocationWeatherResponse(Long locationId, String locationName, double temperature, double humidity, double cloudiness) {
    }

    public record LocationWeatherApiResponse(Main main, Clouds clouds) {
        public record Main(@JsonProperty("temp") double temp, @JsonProperty("humidity") double humidity) {}
        public record Clouds(@JsonProperty("all") double all) {}
    }

}
