package site.pistudio.backend.exceptions;

public class InvalidCodeException extends IllegalArgumentException{
    public InvalidCodeException() {
    }

    public InvalidCodeException(String s) {
        super(s);
    }

    public InvalidCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCodeException(Throwable cause) {
        super(cause);
    }
}
