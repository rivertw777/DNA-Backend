package DNA_Backend.api_server.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionMessage {

    USER_NOT_FOUND("해당하는 회원이 없습니다."),
    DUPLICATE_NAME("이미 존재하는 이름입니다."),
    USER_NAME_NOT_FOUND("해당하는 이름을 가진 회원이 없습니다.");

    private final String message;

}