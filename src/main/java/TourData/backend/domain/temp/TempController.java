package TourData.backend.domain.temp;

import TourData.backend.domain.chat.service.ParticipantCountService;
import TourData.backend.domain.facility.model.Facility;
import TourData.backend.domain.facility.model.FacilityType;
import TourData.backend.domain.facility.repository.FacilityRepository;
import TourData.backend.domain.location.model.Location;
import TourData.backend.domain.location.model.LocationName;
import TourData.backend.domain.location.repository.LocationRepository;
import TourData.backend.domain.location.service.LocationLikeCountService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/temp")
public class TempController {

    private final LocationRepository locationRepository;
    private final LocationLikeCountService locationLikeCountService;
    private final ParticipantCountService participantCountService;
    private final FacilityRepository facilityRepository;

    @Operation(summary = "지역 생성")
    @GetMapping("/1")
    public ResponseEntity<Void> temp1() {

        Location location1 = Location.createLocation(LocationName.SOCKCHO, 38.2060, 128.5912,
                "https://images.unsplash.com/photo-1698767676786-03457d067360?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location1);
        locationLikeCountService.initCount(location1.getId());
        participantCountService.initCount(location1.getId());

        Location location2 = Location.createLocation( LocationName.CHUNCHEON, 37.8814, 127.7259,
                "https://images.unsplash.com/photo-1622628036982-f82aa2fd4fc5?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location2);
        locationLikeCountService.initCount(location2.getId());
        participantCountService.initCount(location2.getId());

        Location location3 = Location.createLocation(LocationName.YANGYANG, 38.0861, 128.6000,
                "https://images.unsplash.com/photo-1661926388505-334021844cec?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location3);
        locationLikeCountService.initCount(location3.getId());
        participantCountService.initCount(location3.getId());

        Location location4 = Location.createLocation(LocationName.GANGNEUNG, 37.7515, 128.8760,
                "https://images.unsplash.com/photo-1684042229029-8a899193a8e4?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location4);
        locationLikeCountService.initCount(location4.getId());
        participantCountService.initCount(location4.getId());

        Location location5 = Location.createLocation( LocationName.WONJU, 37.3495, 127.9219,
                "https://images.unsplash.com/photo-1676705909297-9e172058d8d0?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location5);
        locationLikeCountService.initCount(location5.getId());
        participantCountService.initCount(location5.getId());

        Location location6 = Location.createLocation( LocationName.SAMCHEOK, 37.4489, 129.1650,
                "https://images.unsplash.com/photo-1565149394348-c56457810899?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location6);
        locationLikeCountService.initCount(location6.getId());
        participantCountService.initCount(location6.getId());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "시설 생성")
    @GetMapping("/2")
    public ResponseEntity<Void> temp2() {
        Location location1 = locationRepository.findById(1L).get();
        Location location2 = locationRepository.findById(2L).get();
        Location location3 = locationRepository.findById(3L).get();

        Facility facility1 = Facility.createFacility("맥도날드", FacilityType.RESTAURANT, "강원도 속초시",37.3972, 126.9296, location1);
        facilityRepository.save(facility1);

        Facility facility2 = Facility.createFacility("이디야", FacilityType.CAFE,  "강원도 속초시",37.3944, 126.9306, location2);
        facilityRepository.save(facility2);

        Facility facility3 = Facility.createFacility("워커힐", FacilityType.ACCOMMODATION,  "강원도 속초시",37.3944, 126.9306, location3);
        facilityRepository.save(facility3);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "초기화")
    @GetMapping("/3")
    public ResponseEntity<Void> temp3() {
        List<Location> locations = locationRepository.findAll();
        locations.forEach(location -> {
            locationLikeCountService.initCount(location.getId());
            participantCountService.initCount(location.getId());
        });
        return ResponseEntity.ok().build();
    }

}