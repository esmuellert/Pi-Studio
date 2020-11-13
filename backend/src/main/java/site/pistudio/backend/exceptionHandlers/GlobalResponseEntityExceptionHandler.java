package site.pistudio.backend.exceptionHandlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class}) // 404 not found
    protected ResponseEntity<String> handleNoElement(HttpServletRequest request, NoSuchElementException e) {
        return ResponseEntity.notFound().build();
    }



}
