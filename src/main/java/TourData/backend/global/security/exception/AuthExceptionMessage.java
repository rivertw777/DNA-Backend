package TourData.backend.global.security.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionMessage {

    LOGIN_FAILED("로그인에 실패하였습니다. 올바른 정보를 입력해주세요."),
    UNAUTHORIZED("인증이 필요합니다. 로그인해주세요."),
    NO_AUTHORITY("접근 권한이 없습니다.");

    private final String message;

}
