package TourData.backend.domain.schedule.exception;

import TourData.backend.global.dto.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ScheduleExceptionHandler {

    @ExceptionHandler(ScheduleException.class)
    public ResponseEntity<CustomErrorResponse> handleScheduleException(ScheduleException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorResponse(e.getMessage()));
    }

}