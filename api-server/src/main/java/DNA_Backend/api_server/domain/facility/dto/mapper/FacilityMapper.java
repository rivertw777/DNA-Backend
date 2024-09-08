package DNA_Backend.api_server.domain.facility.dto.mapper;

import DNA_Backend.api_server.domain.facility.dto.response.FacilityResponse;
import DNA_Backend.api_server.domain.facility.model.entity.Facility;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FacilityMapper {

    @Mapping(target = "facilityId", source = "id")
    @Mapping(target = "facilityName", source = "name")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "address", source = "address")
    FacilityResponse toResponse(Facility facility);

    List<FacilityResponse> toResponses(List<Facility> facilities);

}
