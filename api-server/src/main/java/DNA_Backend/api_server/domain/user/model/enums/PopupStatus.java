package DNA_Backend.api_server.domain.user.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PopupStatus {

    FIRST_LOGIN("first-login"),
    REVIEW_WRITING("review-writing"),
    NONE("none");

    private final String value;

}
