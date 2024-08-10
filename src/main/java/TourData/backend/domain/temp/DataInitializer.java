package TourData.backend.domain.temp;

import TourData.backend.domain.chat.service.ParticipantCountService;
import TourData.backend.domain.facility.model.entity.Facility;
import TourData.backend.domain.facility.model.enums.FacilityType;
import TourData.backend.domain.facility.repository.FacilityRepository;
import TourData.backend.domain.location.model.entity.Location;
import TourData.backend.domain.location.model.enums.LocationName;
import TourData.backend.domain.location.repository.LocationRepository;
import TourData.backend.domain.location.service.LocationLikeCountService;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final LocationRepository locationRepository;
    private final LocationLikeCountService locationLikeCountService;
    private final ParticipantCountService participantCountService;
    private final FacilityRepository facilityRepository;

    @Override
    public void run(String... args) {

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


        Facility facility1 = Facility.createFacility("맥도날드", FacilityType.RESTAURANT, LocationName.SOCKCHO,"강원도 속초시",37.3972, 126.9296);
        facilityRepository.save(facility1);

        Facility facility2 = Facility.createFacility("이디야", FacilityType.CAFE, LocationName.CHUNCHEON, "강원도 속초시",37.3944, 126.9306);
        facilityRepository.save(facility2);
    }

    @PreDestroy
    public void cleanup() {
        facilityRepository.deleteAll();
        locationRepository.deleteAll();
    }

}