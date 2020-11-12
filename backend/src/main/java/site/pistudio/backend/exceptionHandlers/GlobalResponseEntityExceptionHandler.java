package site.pistudio.backend.exceptionHandlers;

import com.auth0.jwt.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import site.pistudio.backend.exceptions.TokenIllegalException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class}) // 404 not found
    protected ResponseEntity<String> handleNoElement(HttpServletRequest request, NoSuchElementException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = {JWTCreationException.class, JWTDecodeException.class, TokenIllegalException.class,
            AlgorithmMismatchException.class, SignatureVerificationException.class, InvalidClaimException.class,
            JWTVerificationException.class, IOException.class}) // 500 internal server error (some actual error)
    protected ResponseEntity<String> handleJWTErrors(HttpServletRequest request, RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some server error, please try again");
    }

    @ExceptionHandler(value = {RestClientException.class})
    protected ResponseEntity<String> handleNetworkErrors(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("Internet error");
    }
}
