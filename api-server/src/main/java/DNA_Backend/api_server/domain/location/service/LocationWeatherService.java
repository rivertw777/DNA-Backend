package DNA_Backend.api_server.domain.location.service;

import static DNA_Backend.api_server.domain.location.message.LocationExceptionMessage.LOCATION_WEATHER_REQUEST_FAILED;

import DNA_Backend.api_server.domain.location.dto.LocationWeatherDto.LocationWeatherApiResponse;
import DNA_Backend.api_server.domain.location.dto.LocationWeatherDto.LocationWeatherResponse;
import DNA_Backend.api_server.domain.location.model.entity.Location;
import DNA_Backend.api_server.global.exception.DnaApplicationException;
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

    public List<LocationWeatherResponse> getAllLocationWeathers(List<Location> locations) {
        return locations.stream()
                .map(this::getLocationWeather)
                .collect(Collectors.toList());
    }

    public LocationWeatherResponse getLocationWeather(Location location) {
        String url = String.format("%s?lat=%f&lon=%f&appid=%s&units=metric",
                weatherApiUrl, location.getLatitude(), location.getLongitude(), weatherApiKey);
        LocationWeatherApiResponse response = getLocationWeatherApiResponse(url);
        return new LocationWeatherResponse(
                location.getId(),
                response.main().temp(),
                response.main().humidity(),
                response.clouds().all());
    }

    private LocationWeatherApiResponse getLocationWeatherApiResponse(String url) {
        try {
            LocationWeatherApiResponse response = restTemplate.getForObject(url, LocationWeatherApiResponse.class);
            return response;
        } catch (Exception e) {
            throw new DnaApplicationException(LOCATION_WEATHER_REQUEST_FAILED.getValue());
        }
    }

}
