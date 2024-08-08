package TourData.backend.domain.location.service;

import TourData.backend.domain.location.dto.WeatherDto.WeatherApiResponse;
import TourData.backend.domain.location.dto.WeatherDto.WeatherResponse;
import TourData.backend.domain.location.model.entity.Location;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    private final RestTemplate restTemplate;

    public List<WeatherResponse> getWeatherResponses(List<Location> locations) {
        return locations.stream()
                .map(this::getWeatherResponse)
                .collect(Collectors.toList());
    }

    private WeatherResponse getWeatherResponse(Location location) {
        String url = String.format("%s?lat=%f&lon=%f&appid=%s&units=metric",
                weatherApiUrl, location.getLatitude(), location.getLongitude(), weatherApiKey);
        WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);

        return new WeatherResponse(
                location.getName(),
                response.main().temp(),
                response.main().humidity(),
                response.clouds().all());
    }

}
