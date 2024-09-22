package DNA_Backend.api_server.domain.workationOffice.repository;

import DNA_Backend.api_server.domain.workationOffice.model.entity.WorkationOfficeBookmark;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkationOfficeBookmarkRepository extends JpaRepository<WorkationOfficeBookmark, Long> {
    Optional<WorkationOfficeBookmark> findByUserIdAndWorkationOfficeId(Long userId, Long officeId);
    void deleteByUserIdAndWorkationOfficeId(Long userId, Long officeId);
    List<WorkationOfficeBookmark> findByUserId(Long userId);
}
