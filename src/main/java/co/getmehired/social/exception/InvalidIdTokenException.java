package co.getmehired.social.exception;

public class InvalidIdTokenException extends RuntimeException {

    public InvalidIdTokenException() {
    }

    public InvalidIdTokenException(String s) {
        super(s);
    }

    public InvalidIdTokenException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidIdTokenException(Throwable throwable) {
        super(throwable);
    }

    protected InvalidIdTokenException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
