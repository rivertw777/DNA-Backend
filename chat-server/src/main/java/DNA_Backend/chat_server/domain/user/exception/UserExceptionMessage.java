package DNA_Backend.chat_server.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionMessage {

    USER_NAME_NOT_FOUND("해당하는 이름을 가진 회원이 없습니다.");

    private final String message;

}