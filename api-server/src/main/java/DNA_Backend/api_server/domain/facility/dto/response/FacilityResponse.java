package DNA_Backend.api_server.domain.facility.dto.response;

public record FacilityResponse(
        Long facilityId,
        String facilityName,
        String type,
        String address,
        double latitude,
        double longitude
){
}
