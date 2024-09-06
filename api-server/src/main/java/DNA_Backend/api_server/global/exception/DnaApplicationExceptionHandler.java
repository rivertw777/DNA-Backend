package DNA_Backend.api_server.global.exception;

import DNA_Backend.api_server.global.dto.ErrorResponseCustom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class DnaApplicationExceptionHandler {
    @ExceptionHandler(DnaApplicationException.class)
    public ResponseEntity<ErrorResponseCustom> handleDnaApplicationException(DnaApplicationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseCustom(e.getMessage()));
    }
}
