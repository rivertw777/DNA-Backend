package TourData.backend.domain.location.temp;

import TourData.backend.domain.location.model.Location;
import TourData.backend.domain.location.repository.LocationRepository;
import TourData.backend.domain.location.service.LocationLikeCountService;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationDataInitializer implements CommandLineRunner {

    private final LocationRepository locationRepository;
    private final LocationLikeCountService locationLikeCountService;

    @Override
    public void run(String... args) {

        Location location1 = new Location("Sokcho", 38.2072, 128.5911,
                "https://images.unsplash.com/photo-1698767676786-03457d067360?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location1);
        locationLikeCountService.initCount(location1.getId());

        Location location2 = new Location("Chuncheon", 37.8825, 127.7295,
                "https://images.unsplash.com/photo-1622628036982-f82aa2fd4fc5?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location2);
        locationLikeCountService.initCount(location2.getId());

        Location location3 = new Location("Yangyang", 38.1304, 128.6615,
                "https://images.unsplash.com/photo-1661926388505-334021844cec?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location3);
        locationLikeCountService.initCount(location3.getId());

        Location location4 = new Location("Gangneung", 37.7572, 128.8760,
                "https://images.unsplash.com/photo-1684042229029-8a899193a8e4?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location4);
        locationLikeCountService.initCount(location4.getId());

        Location location5 = new Location("Taebaek", 37.1105, 128.9861,
                "https://images.unsplash.com/photo-1676705909297-9e172058d8d0?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location5);
        locationLikeCountService.initCount(location5.getId());

        Location location6 = new Location("Yeongwol", 37.6778, 128.5573,
                "https://images.unsplash.com/photo-1565149394348-c56457810899?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location6);
        locationLikeCountService.initCount(location6.getId());

        Location location7 = new Location("Donghae", 37.5202, 129.1008,
                "https://images.unsplash.com/photo-1644648123423-d3bd4b7ccc33?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location7);
        locationLikeCountService.initCount(location7.getId());
    }

    @PreDestroy
    public void cleanup() {
        locationRepository.deleteAll();
    }

}