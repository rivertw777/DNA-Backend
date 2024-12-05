package DNA_Backend.api_server.domain.workationReview.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkationReviewExceptionMessage {

    ALREADY_EXISTS("A review already exists for this schedule.");

    private final String value;

}
