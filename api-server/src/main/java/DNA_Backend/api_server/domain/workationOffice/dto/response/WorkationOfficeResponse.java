package DNA_Backend.api_server.domain.workationOffice.dto.response;

public record WorkationOfficeResponse(Long facilityId, String facilityName, String type, String address, double latitude,
                                      double longitude, int level) {
}
