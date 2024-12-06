package DNA_Backend.api_server.domain.workationOffice.repository;

import DNA_Backend.api_server.domain.workationOffice.model.entity.WorkationOfficeBookmark;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkationOfficeBookmarkRepository extends JpaRepository<WorkationOfficeBookmark, Long> {
    Optional<WorkationOfficeBookmark> findByUserIdAndWorkationOfficeId(Long userId, Long officeId);

    void deleteByUserIdAndWorkationOfficeId(Long userId, Long officeId);

    @Query("SELECT wob FROM WorkationOfficeBookmark wob " +
            "JOIN FETCH wob.workationOffice wo " +
            "JOIN FETCH wo.location " +
            "WHERE wob.user.id = :userId " +
            "ORDER BY wob.createdAt DESC")
    List<WorkationOfficeBookmark> findByUserIdWithFetchJoin(Long userId);
}
