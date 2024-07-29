package TourData.backend.domain.facility.service;

import static TourData.backend.domain.facility.exception.FacilityExceptionMessage.ALREADY_BOOKMARK;
import static TourData.backend.domain.facility.exception.FacilityExceptionMessage.ALREADY_UNBOOKMARK;

import TourData.backend.domain.facility.dto.FacilityDto.FacilityBookmarkCheckResponse;
import TourData.backend.domain.facility.dto.FacilityDto.FacilityBookmarkResponse;
import TourData.backend.domain.facility.exception.FacilityException;
import TourData.backend.domain.facility.model.Facility;
import TourData.backend.domain.facility.model.FacilityBookmark;
import TourData.backend.domain.facility.repository.FacilityBookmarkRepository;
import TourData.backend.domain.user.model.User;
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
    private final UserService userService;
    private final FacilityService facilityService;

    // 시설 북마크
    @Transactional
    public void bookmarkFacility(String username, Long facilityId) {
        User user = userService.findUser(username);
        Facility facility = facilityService.findFacility(facilityId);
        validateBookmarkNotExists(user, facility);
        saveFacilityBookmark(user, facility);
    }

    private void validateBookmarkNotExists(User user, Facility facility) {
        if (facilityBookmarkRepository.findByUserAndFacility(user, facility).isPresent()) {
            throw new FacilityException(ALREADY_BOOKMARK.getMessage());
        }
    }

    private void saveFacilityBookmark(User user, Facility facility) {
        FacilityBookmark facilityBookmark = FacilityBookmark.builder()
                .user(user)
                .facility(facility)
                .build();
        facilityBookmarkRepository.save(facilityBookmark);
    }

    // 시설 북마크 취소
    @Transactional
    public void unbookmarkFacility(String username, Long facilityId) {
        User user = userService.findUser(username);
        Facility facility = facilityService.findFacility(facilityId);
        validateBookmarkExists(user, facility);
        facilityBookmarkRepository.deleteByUserAndFacility(user, facility);
    }

    private void validateBookmarkExists(User user, Facility facility) {
        if (facilityBookmarkRepository.findByUserAndFacility(user, facility).isEmpty()) {
            throw new FacilityException(ALREADY_UNBOOKMARK.getMessage());
        }
    }

    // 시설 북마크 여부 확인
    @Transactional(readOnly = true)
    public FacilityBookmarkCheckResponse checkFacilityBookmark(String username, Long facilityId) {
        User user = userService.findUser(username);
        Facility facility = facilityService.findFacility(facilityId);
        boolean isBookmark = facilityBookmarkRepository.findByUserAndFacility(user, facility).isPresent();
        return new FacilityBookmarkCheckResponse(isBookmark);
    }

    // 북마크 시설 전체 조회
    @Transactional(readOnly = true)
    public List<FacilityBookmarkResponse> getAllBookmarks(String username) {
        User user = userService.findUser(username);
        return facilityBookmarkRepository.findByUser(user).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private FacilityBookmarkResponse convertToResponse(FacilityBookmark facilityBookmark) {
        Facility facility = facilityBookmark.getFacility();
        return new FacilityBookmarkResponse(
                facility.getId(),
                facility.getName(),
                facility.getType().getValue(),
                facility.getAddress()
        );
    }

}
