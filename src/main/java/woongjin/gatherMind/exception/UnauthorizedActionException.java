package woongjin.gatherMind.exception;

public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException(String message) {
        super(message);
    }

    public UnauthorizedActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedActionException() {
        super("권한이 없습니다");
    }

}
