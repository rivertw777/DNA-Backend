package DNA_Backend.api_server.domain.facility.dto.response;

public record BookmarkedFacilityResponse(Long facilityId, String facilityName, String type, String address,
                                         String locationName) {
}
