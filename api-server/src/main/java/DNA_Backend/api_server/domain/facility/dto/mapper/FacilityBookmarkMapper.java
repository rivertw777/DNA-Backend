package DNA_Backend.api_server.domain.facility.dto.mapper;

import DNA_Backend.api_server.domain.facility.dto.response.BookmarkedFacilityResponse;
import DNA_Backend.api_server.domain.facility.model.entity.FacilityBookmark;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FacilityBookmarkMapper {

    @Mapping(target = "facilityId", source = "facility.id")
    @Mapping(target = "facilityName", source = "facility.name")
    @Mapping(target = "type", source = "facility.type.value")
    @Mapping(target = "address", source = "facility.address")
    @Mapping(target = "locationName", source = "facility.location.name.value")
    BookmarkedFacilityResponse toResponse(FacilityBookmark facilityBookmark);

    List<BookmarkedFacilityResponse> toResponses(List<FacilityBookmark> facilityBookmarks);

}
