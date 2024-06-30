package TourData.backend.domain.location.dto;

import TourData.backend.domain.location.dto.WeatherDto.WeatherInfo;

public class LocationDto {
    public record LocationResponse(Long id, String name, String thumbNail, WeatherInfo weatherInfo) {
    }

    public record LocationLikeCheckResponse (boolean isLike) {
    }

    public record LocationLikeCountResponse (Integer likeCount) {
    }
}
