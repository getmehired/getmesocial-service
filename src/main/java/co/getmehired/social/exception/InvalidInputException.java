package co.getmehired.social.exception;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException() {
    }

    public InvalidInputException(String s) {
        super(s);
    }

    public InvalidInputException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidInputException(Throwable throwable) {
        super(throwable);
    }

    protected InvalidInputException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
