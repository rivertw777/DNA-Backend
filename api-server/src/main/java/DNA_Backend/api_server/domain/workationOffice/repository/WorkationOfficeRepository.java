package DNA_Backend.api_server.domain.workationOffice.repository;

import DNA_Backend.api_server.domain.workationOffice.model.entity.WorkationOffice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkationOfficeRepository extends JpaRepository<WorkationOffice, Long> {
    List<WorkationOffice> findByLocationId(Long locationId);
}
