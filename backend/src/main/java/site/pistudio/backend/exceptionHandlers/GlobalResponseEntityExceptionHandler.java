package site.pistudio.backend.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import site.pistudio.backend.exceptions.InvalidTokenException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class}) // 404 not found
    protected ResponseEntity<String> handleNoElement(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(value = InvalidTokenException.class) // given token is no longer valid
    protected ResponseEntity<String> handleInvalidToken(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The token is invalid!");
    }

    @ExceptionHandler(value = IllegalArgumentException.class)  // all illegalArgumentException, give information 400
    protected ResponseEntity<String> handleIllegalArgument(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
