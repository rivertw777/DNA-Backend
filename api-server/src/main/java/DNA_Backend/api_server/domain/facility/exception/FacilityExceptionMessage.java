package DNA_Backend.api_server.domain.facility.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FacilityExceptionMessage {

    FACILITY_NOT_FOUND("Facility not found."),
    ALREADY_BOOKMARK("Already bookmarked."),
    ALREADY_UNBOOKMARK("Already unbookmarked."),
    UNKNOWN_TYPE("Unknown facility type.");

    private final String value;

}

