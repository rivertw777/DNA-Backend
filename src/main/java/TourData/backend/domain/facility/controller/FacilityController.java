package TourData.backend.domain.facility.controller;

import TourData.backend.domain.facility.dto.FacilityDto.FacilitySearchResponse;
import TourData.backend.domain.facility.service.FacilityService;
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
@RequestMapping("/api/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @Operation(summary = "시설 검색")
    @GetMapping("/search")
    public ResponseEntity<List<FacilitySearchResponse>> searchFacilities(
            @Valid @RequestParam(name = "latMin") double latMin,
            @Valid @RequestParam(name = "latMax") double latMax,
            @Valid @RequestParam(name = "lngMin") double lngMin,
            @Valid @RequestParam(name = "lngMax") double lngMax,
            @Valid @RequestParam(name = "type") String type) {
        List<FacilitySearchResponse> responses = facilityService.searchFacilities(latMin, latMax, lngMin, lngMax, type);
        return ResponseEntity.ok(responses);
    }

}
