package DNA_Backend.api_server.domain.workationOffice.service;

import DNA_Backend.api_server.domain.workationOffice.dto.response.WorkationOfficeResponse;
import DNA_Backend.api_server.domain.workationOffice.dto.mapper.WorkationOfficeMapper;
import DNA_Backend.api_server.domain.workationOffice.model.entity.WorkationOffice;
import DNA_Backend.api_server.domain.workationOffice.repository.WorkationOfficeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkationOfficeService {

    private final WorkationOfficeRepository workationOfficeRepository;
    private final WorkationOfficeMapper workationOfficeMapper;

    public List<WorkationOfficeResponse> searchWorkationOfficesByLocationId(Long locationId) {
        List<WorkationOffice> workationOffices = workationOfficeRepository.findByLocationId(locationId);
        return workationOfficeMapper.toResponses(workationOffices);
    }

}
