package DNA_Backend.api_server.domain.recommendedLocation.dto.mapper;

import DNA_Backend.api_server.domain.recommendedLocation.dto.response.RecommendedLocationResponse;
import DNA_Backend.api_server.domain.recommendedLocation.model.entity.RecommendedLocation;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecommendedLocationMapper {

    @Mapping(target = "locationId", source = "location.id")
    @Mapping(target = "locationName", source = "location.name.value")
    @Mapping(target = "thumbnail", source = "location.thumbnail")
    @Mapping(target = "latitude", source = "location.latitude")
    @Mapping(target = "longitude", source = "location.longitude")
    @Mapping(target = "keyword", source = "location.keyword")
    @Mapping(target = "description", source = "location.description")
    RecommendedLocationResponse toResponse(RecommendedLocation recommendedLocation);

    List<RecommendedLocationResponse> toResponses(List<RecommendedLocation> recommendedLocations);

}

