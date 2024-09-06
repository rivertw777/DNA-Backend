package DNA_Backend.api_server.domain.facility.dto.response;

import com.querydsl.core.annotations.QueryProjection;

public record LocationTotalFacilityCountResponse(Long locationId, long facilityCount) {
    @QueryProjection
    public LocationTotalFacilityCountResponse {
    }
}