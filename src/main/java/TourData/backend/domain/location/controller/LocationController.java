package TourData.backend.domain.location.controller;

import TourData.backend.domain.location.dto.LocationDto.LocationResponse;
import TourData.backend.domain.location.service.LocationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationSerivce;

    // 게시물 조회
    @GetMapping
    public List<LocationResponse> getAllPosts(){
        return locationSerivce.getAllLocations();
    }

}
