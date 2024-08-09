package TourData.backend.domain.facility.dto;

public class FacilityDto {

    public record FacilitySearchResponse(Long id, String facilityName, String type, String address, double latitude, double longitude){
    }

    public record FacilityBookmarkCheckResponse(boolean isBookmark) {
    }

    public record BookmarkedFacilityResponse(Long id, String facilityName, String type, String address){
    }

    public record LocationFacilitiesCountResponse(int facilitiesCount) {
    }

}
