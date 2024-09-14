package DNA_Backend.api_server.domain.workationOffice.dto.response;

public record WorkationOfficeResponse(Long workationOfficeId, String workationName, String address, double latitude,
                                      double longitude, int level) {
}
