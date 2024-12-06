package DNA_Backend.api_server.domain.facility.repository;

import DNA_Backend.api_server.domain.facility.model.entity.Facility;
import DNA_Backend.api_server.domain.facility.model.enums.FacilityType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long>, FacilityRepositoryCustom {
    List<Facility> findByLocationIdAndType(Long locationId, FacilityType type);

    long countByLocationId(Long locationId);
}
