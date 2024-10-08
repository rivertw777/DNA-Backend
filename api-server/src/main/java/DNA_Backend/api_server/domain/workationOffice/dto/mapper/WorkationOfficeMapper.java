package DNA_Backend.api_server.domain.workationOffice.dto.mapper;

import DNA_Backend.api_server.domain.workationOffice.dto.response.WorkationOfficeDetailResponse;
import DNA_Backend.api_server.domain.workationOffice.dto.response.WorkationOfficeResponse;
import DNA_Backend.api_server.domain.workationOffice.model.entity.WorkationOffice;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkationOfficeMapper {

    @Mapping(target = "facilityId", source = "id")
    @Mapping(target = "facilityName", source = "name")
    @Mapping(target = "type", expression = "java(\"Workation Office\")")
    WorkationOfficeResponse toResponse(WorkationOffice workationOffice);

    List<WorkationOfficeResponse> toResponses(List<WorkationOffice> workationOffices);

    @Mapping(target = "workationOfficeId", source = "id")
    @Mapping(target = "workationOfficeName", source = "name")
    WorkationOfficeDetailResponse toDetailResponse(WorkationOffice workationOffice);

}