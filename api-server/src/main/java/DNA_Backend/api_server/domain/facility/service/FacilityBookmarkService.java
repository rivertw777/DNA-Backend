package DNA_Backend.api_server.domain.facility.service;

import static DNA_Backend.api_server.domain.facility.message.FacilityExceptionMessage.ALREADY_BOOKMARK;
import static DNA_Backend.api_server.domain.facility.message.FacilityExceptionMessage.ALREADY_UNBOOKMARK;

import DNA_Backend.api_server.domain.facility.dto.response.BookmarkedFacilityResponse;
import DNA_Backend.api_server.domain.facility.dto.response.CheckFacilityBookmarkResponse;
import DNA_Backend.api_server.domain.facility.model.entity.Facility;
import DNA_Backend.api_server.domain.facility.model.entity.FacilityBookmark;
import DNA_Backend.api_server.domain.facility.repository.FacilityBookmarkRepository;
import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.user.service.UserService;
import DNA_Backend.api_server.global.exception.DnaApplicationException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FacilityBookmarkService {

    private final FacilityBookmarkRepository facilityBookmarkRepository;
    private final FacilityService facilityService;
    private final UserService userService;

    // USER - 시설 북마크
    @Transactional
    public void bookmarkFacility(Long userId, Long facilityId) {
        User user = userService.findUser(userId);
        Facility facility = facilityService.findFacility(facilityId);
        validateBookmarkNotExists(user.getId(), facilityId);
        saveFacilityBookmark(user, facility);
    }

    private void validateBookmarkNotExists(Long userId, Long facilityId) {
        if (facilityBookmarkRepository.findByUserIdAndFacilityId(userId, facilityId).isPresent()) {
            throw new DnaApplicationException(ALREADY_BOOKMARK.getValue());
        }
    }

    private void saveFacilityBookmark(User user, Facility facility) {
        FacilityBookmark facilityBookmark = FacilityBookmark.createFacilityBookmark(user, facility);
        facilityBookmarkRepository.save(facilityBookmark);
    }

    // USER - 시설 북마크 취소
    @Transactional
    public void unbookmarkFacility(Long userId, Long facilityId) {
        validateBookmarkExists(userId, facilityId);
        facilityBookmarkRepository.deleteByUserIdAndFacilityId(userId, facilityId);
    }

    private void validateBookmarkExists(Long userId, Long facilityId) {
        if (facilityBookmarkRepository.findByUserIdAndFacilityId(userId, facilityId).isEmpty()) {
            throw new DnaApplicationException(ALREADY_UNBOOKMARK.getValue());
        }
    }

    // USER - 시설 북마크 여부 확인
    @Transactional(readOnly = true)
    public CheckFacilityBookmarkResponse checkFacilityBookmark(Long userId, Long facilityId) {
        boolean isBookmarked = facilityBookmarkRepository.findByUserIdAndFacilityId(userId, facilityId).isPresent();
        return new CheckFacilityBookmarkResponse(isBookmarked);
    }

    // USER - 전체 북마크 시설 조회
    @Transactional(readOnly = true)
    public List<BookmarkedFacilityResponse> getAllBookmarkedFacilities(Long userId) {
        return facilityBookmarkRepository.findByUserId(userId).stream()
                .map(BookmarkedFacilityResponse::new)
                .collect(Collectors.toList());
    }

}
