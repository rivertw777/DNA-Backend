package DNA_Backend.api_server.domain.facility.dto.response;

import DNA_Backend.api_server.domain.facility.model.entity.Facility;

public record FacilityResponse(Long facilityId, String facilityName, String type, String address, double latitude, double longitude){
    public FacilityResponse(Facility facility) {
        this(
                facility.getId(),
                facility.getName(),
                facility.getType().getValue(),
                facility.getAddress(),
                facility.getLatitude(),
                facility.getLongitude()
        );
    }
}