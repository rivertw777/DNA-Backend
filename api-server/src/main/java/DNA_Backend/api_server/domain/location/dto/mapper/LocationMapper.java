package DNA_Backend.api_server.domain.location.dto.mapper;

import DNA_Backend.api_server.domain.location.dto.response.LocationDetailResponse;
import DNA_Backend.api_server.domain.location.dto.response.LocationResponse;
import DNA_Backend.api_server.domain.location.model.entity.Location;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "locationId", source = "id")
    @Mapping(target = "locationName", source = "name.value")
    LocationResponse toResponse(Location location);

    List<LocationResponse> toResponses(List<Location> locations);

    @Mapping(target = "locationId", source = "id")
    @Mapping(target = "locationName", source = "name.value")
    LocationDetailResponse toDetailResponse(Location location);

}
