package DNA_Backend.api_server.domain.workationOffice.dto.mapper;

import DNA_Backend.api_server.domain.workationOffice.dto.response.BookmarkedWorkationOfficeResponse;
import DNA_Backend.api_server.domain.workationOffice.model.entity.WorkationOfficeBookmark;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkationOfficeBookmarkMapper {

    @Mapping(target = "officeId", source = "workationOffice.id")
    @Mapping(target = "officeName", source = "workationOffice.name")
    @Mapping(target = "type", expression = "java(\"Workation Office\")")
    @Mapping(target = "address", source = "workationOffice.address")
    @Mapping(target = "locationName", source = "workationOffice.location.name.value")
    BookmarkedWorkationOfficeResponse toResponse(WorkationOfficeBookmark workationOfficeBookmark);

    List<BookmarkedWorkationOfficeResponse> toResponses(List<WorkationOfficeBookmark> workationOfficeBookmarks);

}
