package DNA_Backend.api_server.domain.workationOffice.dto.response;

public record WorkationOfficeDetailResponse(Long workationOfficeId, String workationName, String address, double latitude,
                                      double longitude, String openTime, String telNumber, int level) {
}