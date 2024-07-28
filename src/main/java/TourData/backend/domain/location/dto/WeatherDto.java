package TourData.backend.domain.location.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDto {

    public record WeatherResponse(String locationName, double temperature, double humidity, double cloudiness) {
    }

    public record WeatherApiResponse(Main main, Clouds clouds) {
        public record Main(@JsonProperty("temp") double temp, @JsonProperty("humidity") double humidity) {}
        public record Clouds(@JsonProperty("all") double all) {}
    }

}
