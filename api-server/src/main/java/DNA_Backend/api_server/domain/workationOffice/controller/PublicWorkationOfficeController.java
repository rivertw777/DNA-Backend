package DNA_Backend.api_server.domain.workationOffice.controller;

import DNA_Backend.api_server.domain.workationOffice.dto.response.WorkationOfficeDetailResponse;
import DNA_Backend.api_server.domain.workationOffice.service.WorkationOfficeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/workation-offices")
public class PublicWorkationOfficeController {

    private final WorkationOfficeService workationOfficeService;

    @Operation(summary = "PUBLIC - 단일 워케이션 오피스 상세 조회")
    @GetMapping("/{officeId}")
    public ResponseEntity<WorkationOfficeDetailResponse> getWorkationOfficeDetail(@Valid @PathVariable("officeId") Long officeId) {
        WorkationOfficeDetailResponse response = workationOfficeService.getWorkationOfficeDetail(officeId);
        return ResponseEntity.ok(response);
    }

}
