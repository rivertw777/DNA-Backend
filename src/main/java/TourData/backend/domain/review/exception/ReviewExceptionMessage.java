package TourData.backend.domain.review.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewExceptionMessage {

    REVIEW_ALREADY_EXISTS("일정에 이미 리뷰가 존재합니다.");

    private final String message;

}