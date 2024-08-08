package TourData.backend.domain.facility.service;

import static TourData.backend.domain.facility.exception.FacilityExceptionMessage.ALREADY_BOOKMARK;
import static TourData.backend.domain.facility.exception.FacilityExceptionMessage.ALREADY_UNBOOKMARK;

import TourData.backend.domain.facility.dto.FacilityDto.BookmarkedFacilityResponse;
import TourData.backend.domain.facility.dto.FacilityDto.FacilityBookmarkCheckResponse;
import TourData.backend.domain.facility.exception.FacilityException;
import TourData.backend.domain.facility.model.entity.Facility;
import TourData.backend.domain.facility.model.entity.FacilityBookmark;
import TourData.backend.domain.facility.repository.FacilityBookmarkRepository;
import TourData.backend.domain.user.model.entity.User;
import TourData.backend.domain.user.service.UserService;
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

    // 사용자 시설 북마크
    @Transactional
    public void bookmarkFacility(Long userId, Long facilityId) {
        User user = userService.findUser(userId);
        Facility facility = facilityService.findFacility(facilityId);
        validateBookmarkNotExists(user.getId(), facilityId);
        saveFacilityBookmark(user, facility);
    }

    private void validateBookmarkNotExists(Long userId, Long facilityId) {
        if (facilityBookmarkRepository.findByUserIdAndFacilityId(userId, facilityId).isPresent()) {
            throw new FacilityException(ALREADY_BOOKMARK.getMessage());
        }
    }

    private void saveFacilityBookmark(User user, Facility facility) {
        FacilityBookmark facilityBookmark = FacilityBookmark.createFacilityBookmark(user, facility);
        facilityBookmarkRepository.save(facilityBookmark);
    }

    // 사용자 시설 북마크 취소
    @Transactional
    public void unbookmarkFacility(Long userId, Long facilityId) {
        validateBookmarkExists(userId, facilityId);
        facilityBookmarkRepository.deleteByUserIdAndFacilityId(userId, facilityId);
    }

    private void validateBookmarkExists(Long userId, Long facilityId) {
        if (facilityBookmarkRepository.findByUserIdAndFacilityId(userId, facilityId).isEmpty()) {
            throw new FacilityException(ALREADY_UNBOOKMARK.getMessage());
        }
    }

    // 사용자 시설 북마크 여부 확인
    @Transactional(readOnly = true)
    public FacilityBookmarkCheckResponse checkFacilityBookmark(Long userId, Long facilityId) {
        boolean isBookmark = facilityBookmarkRepository.findByUserIdAndFacilityId(userId, facilityId).isPresent();
        return new FacilityBookmarkCheckResponse(isBookmark);
    }

    // 사용자 북마크 시설 전체 조회
    @Transactional(readOnly = true)
    public List<BookmarkedFacilityResponse> getAllBookmarks(Long userId) {
        return facilityBookmarkRepository.findByUserId(userId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private BookmarkedFacilityResponse toResponseDto(FacilityBookmark facilityBookmark) {
        Facility facility = facilityBookmark.getFacility();
        return new BookmarkedFacilityResponse(
                facility.getId(),
                facility.getName(),
                facility.getType().getValue(),
                facility.getAddress()
        );
    }

}
