package DNA_Backend.api_server.domain.location.dto.response;

import DNA_Backend.api_server.domain.location.model.entity.Location;

public record LocationResponse(Long locationId, String locationName, String thumbNail,
                               double latitude, double longitude) {
    public LocationResponse(Location location) {
        this(
                location.getId(),
                location.getName().getValue(),
                location.getThumbnail(),
                location.getLatitude(),
                location.getLongitude()
        );
    }
}