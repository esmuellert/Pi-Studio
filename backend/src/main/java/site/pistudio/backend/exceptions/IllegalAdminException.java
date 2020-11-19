package site.pistudio.backend.exceptions;

public class IllegalAdminException extends IllegalArgumentException{
    public IllegalAdminException() {
    }

    public IllegalAdminException(String s) {
        super(s);
    }

    public IllegalAdminException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAdminException(Throwable cause) {
        super(cause);
    }
}
