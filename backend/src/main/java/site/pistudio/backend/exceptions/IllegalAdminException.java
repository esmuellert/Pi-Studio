package site.pistudio.backend.exceptions;

public class IllegalAdminException extends IllegalAccessException{
    public IllegalAdminException() {
    }

    public IllegalAdminException(String s) {
        super(s);
    }
}
