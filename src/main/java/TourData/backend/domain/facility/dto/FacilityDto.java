package TourData.backend.domain.facility.dto;

public class FacilityDto {

    public record FacilitySearchResponse(String name, String type, String address, double latitude, double longitude){
    }

}
