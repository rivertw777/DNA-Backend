package DNA_Backend.api_server.domain.facility.repository;

import DNA_Backend.api_server.domain.facility.dto.FacilityDto.LocationTotalFacilityCountResponse;
import java.util.List;

public interface FacilityRepositoryCustom {
    List<LocationTotalFacilityCountResponse> countTotalFacilitiesGroupedByLocation();
}
