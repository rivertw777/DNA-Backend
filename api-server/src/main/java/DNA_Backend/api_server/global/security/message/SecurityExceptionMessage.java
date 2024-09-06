package DNA_Backend.api_server.global.security.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityExceptionMessage {

    UNAUTHORIZED("인증이 필요합니다. 로그인해주세요."),
    NO_AUTHORITY("접근 권한이 없습니다.");

    private final String value;

}
