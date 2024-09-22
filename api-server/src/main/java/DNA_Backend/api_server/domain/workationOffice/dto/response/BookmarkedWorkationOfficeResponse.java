package DNA_Backend.api_server.domain.workationOffice.dto.response;

public record BookmarkedWorkationOfficeResponse(Long officeId, String officeName, String type, String address,
                                                String locationName) {
}
