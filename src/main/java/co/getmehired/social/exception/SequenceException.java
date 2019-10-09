package co.getmehired.social.exception;


public class SequenceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;

    public SequenceException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
