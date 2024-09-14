package DNA_Backend.api_server.domain.facility.controller;

import DNA_Backend.api_server.domain.facility.dto.response.FacilityResponse;
import DNA_Backend.api_server.domain.facility.service.FacilityService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/facilities")
public class PublicFacilityController {

    private final FacilityService facilityService;

    @Operation(summary = "PUBLIC - 시설 검색 by 위도, 경도 & 타입")
    @GetMapping("/search")
    public ResponseEntity<List<FacilityResponse>> searchFacilities(
            @Valid @RequestParam(name = "latMin") Double latMin, @Valid @RequestParam(name = "latMax") Double latMax,
            @Valid @RequestParam(name = "lngMin") Double lngMin, @Valid @RequestParam(name = "lngMax") Double lngMax,
            @Valid @RequestParam(name = "facilityType") String facilityType) {
        List<FacilityResponse> responses = facilityService.searchFacilities(latMin, latMax, lngMin, lngMax, facilityType);
        return ResponseEntity.ok(responses);
    }

}
