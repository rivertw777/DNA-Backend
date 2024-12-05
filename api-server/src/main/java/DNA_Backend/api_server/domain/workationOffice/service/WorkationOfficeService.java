package DNA_Backend.api_server.domain.workationOffice.service;

import static DNA_Backend.api_server.domain.workationOffice.exception.WorkationOfficeExceptionMessage.OFFICE_NOT_FOUND;

import DNA_Backend.api_server.domain.workationOffice.dto.response.WorkationOfficeDetailResponse;
import DNA_Backend.api_server.domain.workationOffice.dto.response.WorkationOfficeResponse;
import DNA_Backend.api_server.domain.workationOffice.dto.mapper.WorkationOfficeMapper;
import DNA_Backend.api_server.domain.workationOffice.model.entity.WorkationOffice;
import DNA_Backend.api_server.domain.workationOffice.repository.WorkationOfficeRepository;
import DNA_Backend.api_server.common.exception.DnaApplicationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkationOfficeService {

    private final WorkationOfficeRepository workationOfficeRepository;
    private final WorkationOfficeMapper workationOfficeMapper;

    // id로 조회
    public WorkationOffice findWorkationOffice(Long officeId) {
        return workationOfficeRepository.findById(officeId)
                .orElseThrow(() -> new DnaApplicationException(OFFICE_NOT_FOUND.getValue()));
    }

    // PUBLIC - 워케이션 오피스 검색 by 지역 Id
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "WorkationOfficeSearchResults", key = "#p0", cacheManager = "redisCacheManager")
    public List<WorkationOfficeResponse> searchWorkationOfficesByLocationId(Long locationId) {
        List<WorkationOffice> workationOffices = workationOfficeRepository.findByLocationId(locationId);
        return workationOfficeMapper.toResponses(workationOffices);
    }

    // PUBLIC - 단일 워케이션 오피스 상세 조회
    @Transactional(readOnly = true)
    public WorkationOfficeDetailResponse getWorkationOfficeDetail(Long officeId) {
        WorkationOffice workationOffice = findWorkationOffice(officeId);
        return workationOfficeMapper.toDetailResponse(workationOffice);
    }

}
