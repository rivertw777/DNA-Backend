package DNA_Backend.api_server.domain.review.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewExceptionMessage {

    ALREADY_EXISTS("A review already exists for this schedule.");

    private final String message;

}
