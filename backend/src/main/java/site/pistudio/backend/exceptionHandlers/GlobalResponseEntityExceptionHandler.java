package site.pistudio.backend.exceptionHandlers;

import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ResponseEntity<String> handleNoElement(HttpServletRequest request, NoSuchElementException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = {JWTCreationException.class})
    protected ResponseEntity<String> handleJWTErrors(HttpServletRequest request, RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some server error, please try again");
    }
}
