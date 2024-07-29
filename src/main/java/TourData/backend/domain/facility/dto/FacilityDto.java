package TourData.backend.domain.facility.dto;

public class FacilityDto {

    public record FacilitySearchResponse(Long id, String facilityName, String type, String address, double latitude, double longitude){
    }

    public record FacilityBookmarkCheckResponse(boolean isBookmarked) {
    }

    public record BookmarkedFacilityResponse(Long id, String facilityName, String type, String address){
    }

}
