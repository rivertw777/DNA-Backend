package DNA_Backend.api_server.domain.workationOffice.dto.response;

public record WorkationOfficeDetailResponse(
        Long workationOfficeId,
        String workationOfficeName,
        String address,
        double latitude,
        double longitude,
        String businessHours,
        String telNumber,
        int level
) {
}