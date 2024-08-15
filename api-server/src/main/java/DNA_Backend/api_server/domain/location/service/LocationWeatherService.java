package DNA_Backend.api_server.domain.location.service;

import DNA_Backend.api_server.domain.location.dto.LocationWeatherDto.LocationWeatherApiResponse;
import DNA_Backend.api_server.domain.location.dto.LocationWeatherDto.LocationWeatherResponse;
import DNA_Backend.api_server.domain.location.model.Location;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LocationWeatherService {

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    private final RestTemplate restTemplate;

    public List<LocationWeatherResponse> getWeatherResponsesForAllLocations(List<Location> locations) {
        return locations.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private LocationWeatherResponse toResponseDto(Location location) {
        String url = String.format("%s?lat=%f&lon=%f&appid=%s&units=metric",
                weatherApiUrl, location.getLatitude(), location.getLongitude(), weatherApiKey);
        LocationWeatherApiResponse response = restTemplate.getForObject(url, LocationWeatherApiResponse.class);

        return new LocationWeatherResponse(
                location.getId(),
                location.getName().getValue(),
                response.main().temp(),
                response.main().humidity(),
                response.clouds().all());
    }

}
