package DNA_Backend.api_server.domain.workationOffice.service;

import static DNA_Backend.api_server.domain.facility.exception.FacilityExceptionMessage.ALREADY_BOOKMARK;
import static DNA_Backend.api_server.domain.facility.exception.FacilityExceptionMessage.ALREADY_UNBOOKMARK;

import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.user.service.UserService;
import DNA_Backend.api_server.domain.workationOffice.dto.mapper.WorkationOfficeBookmarkMapper;
import DNA_Backend.api_server.domain.workationOffice.dto.response.BookmarkedWorkationOfficeResponse;
import DNA_Backend.api_server.domain.workationOffice.dto.response.CheckWorkationOfficeBookmarkResponse;
import DNA_Backend.api_server.domain.workationOffice.model.entity.WorkationOffice;
import DNA_Backend.api_server.domain.workationOffice.model.entity.WorkationOfficeBookmark;
import DNA_Backend.api_server.domain.workationOffice.repository.WorkationOfficeBookmarkRepository;
import DNA_Backend.api_server.common.exception.DnaApplicationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkationOfficeBookmarkService {

    private final WorkationOfficeBookmarkRepository workationOfficeBookmarkRepository;
    private final UserService userService;
    private final WorkationOfficeService workationOfficeService;
    private final WorkationOfficeBookmarkMapper workationOfficeBookmarkMapper;

    // USER - 워케이션 오피스 북마크
    @Transactional
    public void bookmarkWorkationOffice(Long userId, Long officeId) {
        User user = userService.findUser(userId);
        WorkationOffice workationOffice = workationOfficeService.findWorkationOffice(officeId);
        validateBookmarkNotExists(userId, officeId);
        saveWorkationOfficeBookmark(user, workationOffice);
    }

    private void validateBookmarkNotExists(Long userId, Long officeId) {
        if (workationOfficeBookmarkRepository.findByUserIdAndWorkationOfficeId(userId, officeId).isPresent()) {
            throw new DnaApplicationException(ALREADY_BOOKMARK.getValue());
        }
    }

    private void saveWorkationOfficeBookmark(User user, WorkationOffice workationOffice) {
        WorkationOfficeBookmark workationOfficeBookmark = WorkationOfficeBookmark.createWorkationOfficeBookmark(user, workationOffice);
        workationOfficeBookmarkRepository.save(workationOfficeBookmark);
    }

    // USER - 워케이션 오피스 북마크 취소
    @Transactional
    public void unbookmarkWorkationOffice(Long userId, Long officeId) {
        validateBookmarkExists(userId, officeId);
        workationOfficeBookmarkRepository.deleteByUserIdAndWorkationOfficeId(userId, officeId);
    }

    private void validateBookmarkExists(Long userId, Long officeId) {
        if (workationOfficeBookmarkRepository.findByUserIdAndWorkationOfficeId(userId, officeId).isEmpty()) {
            throw new DnaApplicationException(ALREADY_UNBOOKMARK.getValue());
        }
    }

    // USER - 워케이션 오피스 북마크 여부 확인
    @Transactional(readOnly = true)
    public CheckWorkationOfficeBookmarkResponse checkWorkationOfficeBookmark(Long userId, Long officeId) {
        boolean isBookmarked = workationOfficeBookmarkRepository.findByUserIdAndWorkationOfficeId(userId, officeId).isPresent();
        return new CheckWorkationOfficeBookmarkResponse(isBookmarked);
    }

    // USER - 전체 북마크 워케이션 오피스 조회
    @Transactional(readOnly = true)
    public List<BookmarkedWorkationOfficeResponse> getAllBookmarkedWorkationOffices(Long userId) {
        List<WorkationOfficeBookmark> workationOfficeBookmarks = workationOfficeBookmarkRepository.findByUserId(userId);
        return workationOfficeBookmarkMapper.toResponses(workationOfficeBookmarks);
    }

}
