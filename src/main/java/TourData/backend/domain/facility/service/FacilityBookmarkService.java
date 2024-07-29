package TourData.backend.domain.facility.service;

import static TourData.backend.domain.facility.exception.FacilityExceptionMessage.ALREADY_BOOKMARK;

import TourData.backend.domain.facility.exception.FacilityException;
import TourData.backend.domain.facility.model.Facility;
import TourData.backend.domain.facility.model.FacilityBookmark;
import TourData.backend.domain.facility.repository.FacilityBookmarkRepository;
import TourData.backend.domain.user.model.User;
import TourData.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FacilityBookmarkService {

    private final FacilityBookmarkRepository facilityBookmarkRepository;
    private final UserService userService;
    private final FacilityService facilityService;

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

}
