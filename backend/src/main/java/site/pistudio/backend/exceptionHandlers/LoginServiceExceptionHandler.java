package site.pistudio.backend.exceptionHandlers;

import com.auth0.jwt.exceptions.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import site.pistudio.backend.exceptions.InvalidCodeException;
import site.pistudio.backend.exceptions.TokenIllegalException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class LoginServiceExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {JWTCreationException.class, TokenIllegalException.class,
            AlgorithmMismatchException.class, SignatureVerificationException.class, InvalidClaimException.class,
            JWTVerificationException.class}) // 500 internal server error (some actual error)
    protected ResponseEntity<String> handleJWTErrors(HttpServletRequest request, RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some server error, please try again");
    }

    @ExceptionHandler(value = {RestClientException.class})
    protected ResponseEntity<String> handleRequestForOpenIdError(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("Internet error");
    }

    @ExceptionHandler(value = {JsonProcessingException.class, JsonMappingException.class, JsonParseException.class})
    protected ResponseEntity<String> handleParseErrors(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("please contact administrator");
    }

    @ExceptionHandler(value = {InvalidCodeException.class})
    protected ResponseEntity<String> handleCodeRepeated(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("please reboot the app");
    }
}
