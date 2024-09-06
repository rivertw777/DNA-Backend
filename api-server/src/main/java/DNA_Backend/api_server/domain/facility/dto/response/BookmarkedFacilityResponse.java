package DNA_Backend.api_server.domain.facility.dto.response;

import DNA_Backend.api_server.domain.facility.model.entity.FacilityBookmark;

public record BookmarkedFacilityResponse(Long facilityId, String facilityName, String type, String address) {
    public BookmarkedFacilityResponse(FacilityBookmark facilityBookmark) {
        this(
                facilityBookmark.getFacility().getId(),
                facilityBookmark.getFacility().getName(),
                facilityBookmark.getFacility().getType().getValue(),
                facilityBookmark.getFacility().getAddress()
        );
    }
}
