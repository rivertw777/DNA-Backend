package TourData.backend.domain.workationSchedule.exception;

import TourData.backend.global.dto.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class WorkationScheduleExceptionHandler {

    @ExceptionHandler(WorkationScheduleException.class)
    public ResponseEntity<CustomErrorResponse> handleScheduleException(WorkationScheduleException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorResponse(e.getMessage()));
    }

}