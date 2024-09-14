package DNA_Backend.api_server.domain.workationOffice.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkationOfficeExceptionMessage {

    OFFICE_NOT_FOUND("Office not found.");

    private final String value;

}

