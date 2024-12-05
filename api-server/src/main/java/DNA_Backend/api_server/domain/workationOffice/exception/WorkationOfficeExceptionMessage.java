package DNA_Backend.api_server.domain.workationOffice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkationOfficeExceptionMessage {

    OFFICE_NOT_FOUND("Office not found.");

    private final String value;

}

