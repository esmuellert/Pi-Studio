package site.pistudio.backend.exceptions;

public class TokenIllegalException extends IllegalArgumentException{
    public TokenIllegalException() {
    }

    public TokenIllegalException(String s) {
        super(s);
    }

    public TokenIllegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenIllegalException(Throwable cause) {
        super(cause);
    }
}
