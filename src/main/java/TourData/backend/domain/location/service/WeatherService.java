package TourData.backend.domain.location.service;

import TourData.backend.domain.location.dto.WeatherDto.WeatherApiResponse;
import TourData.backend.domain.location.dto.WeatherDto.WeatherInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    private final WebClient webClient;

    public WeatherInfo getWeatherInfo(String locationName) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", weatherApiUrl, locationName, weatherApiKey);

        WeatherApiResponse response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .block();

        return new WeatherInfo(
                response.main().temp(),
                response.main().humidity(),
                response.wind().speed(),
                response.clouds().all());
    }

}
