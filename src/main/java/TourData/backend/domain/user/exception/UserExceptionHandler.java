package TourData.backend.domain.user.exception;

import TourData.backend.global.exception.dto.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<CustomErrorResponse> handleMemberException(UserException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorResponse(e.getMessage()));
    }

}