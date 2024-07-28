package TourData.backend.domain.facility.temp;

import TourData.backend.domain.facility.model.Facility;
import TourData.backend.domain.facility.model.FacilityType;
import TourData.backend.domain.facility.repository.FacilityRepository;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FacilityDataInitializer implements CommandLineRunner {

    private final FacilityRepository facilityRepository;

    // http://localhost:8080/api/facilities/search?latMin=37.3940&latMax=37.3980&lngMin=126.9280&lngMax=126.9320&type=restaurant
    @Override
    public void run(String... args) {
        Facility facility1 = Facility.builder()
                .name("맥도날드")
                .address("강원도 속초시")
                .type(FacilityType.RESTAURANT)
                .latitude(37.3972)
                .longitude(126.9296)
                .build();
        facilityRepository.save(facility1);

        Facility facility2 = Facility.builder()
                .name("이디야")
                .address("강원도 속초시")
                .type(FacilityType.CAFE)
                .latitude(37.3944)
                .longitude(126.9306)
                .build();
        facilityRepository.save(facility2);
    }

    @PreDestroy
    public void cleanup() {
        facilityRepository.deleteAll();
    }

}
