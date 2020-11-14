package site.pistudio.backend.exceptionHandlers;

import com.auth0.jwt.exceptions.JWTCreationException;
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

@ControllerAdvice
public class LoginServiceExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {JWTCreationException.class}) // almost impossible error.
    // if exist, contact admin
    protected ResponseEntity<String> handleJWTErrors(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("please contact administrator");
    }

    @ExceptionHandler(value = {RestClientException.class}) // internet error during request for openId
    protected ResponseEntity<String> handleRequestForOpenIdError(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("Internet error");
    }

    @ExceptionHandler(value = {JsonProcessingException.class, JsonMappingException.class, JsonParseException.class})
    // error during parse response body from tencent api, if exist, must call admin
    protected ResponseEntity<String> handleParseErrors(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("please contact administrator");
    }

    @ExceptionHandler(value = {InvalidCodeException.class}) // both code and token are invalid, could be resolved by
    // rebooting the app if the user is a valid wechat user
    protected ResponseEntity<String> handleCodeRepeated(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("please reboot the app");
    }
}
