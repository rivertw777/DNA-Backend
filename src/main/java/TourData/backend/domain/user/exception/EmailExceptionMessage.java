package TourData.backend.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailExceptionMessage {

    SEND_MAIL_FAILED("메일 전송에 실패하였습니다.");

    private final String message;

}