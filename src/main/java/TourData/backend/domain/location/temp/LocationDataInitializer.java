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
        Location location1 = new Location( "Sokcho", "https://cdn.woman.chosun.com/news/photo/202204/97384_80024_1440.jpg");
        locationRepository.save(location1);
        locationLikeCountService.initCount(location1.getId());

        Location location2 = new Location("Chuncheon", "https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202104/15/9dc7194a-30cc-4005-aabc-372e362b29f9.jpg");
        locationRepository.save(location2);
        locationLikeCountService.initCount(location2.getId());

        Location location3 = new Location("Yangyang", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZbXZWlokJE_MLQc1YqROH2doJihNzVIWxqg&s");
        locationRepository.save(location3);
        locationLikeCountService.initCount(location3.getId());

        Location location4 = new Location("Gangneung", "https://images.unsplash.com/photo-1621044332832-717d5d986ab7?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        locationRepository.save(location4);
        locationLikeCountService.initCount(location4.getId());
    }

    @PreDestroy
    public void cleanup() {
        locationRepository.deleteAll();
    }

}