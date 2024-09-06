package DNA_Backend.api_server.domain.location.dto.response;

import DNA_Backend.api_server.domain.location.model.entity.Location;

public record LocationDetailResponse(Long locationId, String locationName, String thumbNail,
                                     double latitude, double longitude, float internetSpeed,
                                     float priceIndex, float populationDensity, double averageRating) {
    public LocationDetailResponse(Location location) {
        this(
                location.getId(),
                location.getName().getValue(),
                location.getThumbnail(),
                location.getLatitude(),
                location.getLongitude(),
                location.getInternetSpeed(),
                location.getPriceIndex(),
                location.getPopulationDensity(),
                location.getAverageRating()
        );
    }
}