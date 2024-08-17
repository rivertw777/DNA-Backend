package DNA_Backend.api_server.domain.facility.dto;

public class FacilityDto {

    public record FacilityResponse(Long facilityId, String facilityName, String type, String address, double latitude, double longitude){
    }

    public record CheckFacilityBookmarkResponse(boolean isBookmarked) {
    }

    public record BookmarkedFacilityResponse(Long facilityId, String facilityName, String type, String address){
    }

    public record LocationTotalFacilityCountResponse(Long locationId, long facilityCount) {
    }

}
