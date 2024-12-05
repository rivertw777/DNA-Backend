package DNA_Backend.api_server.common.email.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailExceptionMessage {

    SEND_MAIL_FAILED("Failed to send email.");

    private final String message;

}

