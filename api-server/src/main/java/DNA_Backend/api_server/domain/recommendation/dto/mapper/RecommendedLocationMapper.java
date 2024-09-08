package DNA_Backend.api_server.domain.recommendation.dto.mapper;

import DNA_Backend.api_server.domain.recommendation.dto.response.RecommendedLocationResponse;
import DNA_Backend.api_server.domain.recommendation.model.entity.RecommendedLocation;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecommendedLocationMapper {

    @Mapping(target = "locationId", source = "location.id")
    @Mapping(target = "locationName", source = "location.name")
    @Mapping(target = "thumbnail", source = "location.thumbnail")
    RecommendedLocationResponse toResponse(RecommendedLocation recommendedLocation);

    List<RecommendedLocationResponse> toResponses(List<RecommendedLocation> recommendedLocations);

}
