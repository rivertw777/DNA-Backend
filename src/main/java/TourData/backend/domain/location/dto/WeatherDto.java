package TourData.backend.domain.location.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDto {

    public record WeatherInfo(double temperature, double humidity, double windSpeed, double cloudiness) {
    }

    public record WeatherApiResponse(Main main, Wind wind, Clouds clouds) {
        public record Main(@JsonProperty("temp") double temp, @JsonProperty("humidity") double humidity) {}
        public record Wind(@JsonProperty("speed") double speed) {}
        public record Clouds(@JsonProperty("all") double all) {}
    }

}
