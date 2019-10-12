package co.getmehired.social.exception;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
    }

    public AccessDeniedException(String s) {
        super(s);
    }

    public AccessDeniedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AccessDeniedException(Throwable throwable) {
        super(throwable);
    }

    protected AccessDeniedException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
