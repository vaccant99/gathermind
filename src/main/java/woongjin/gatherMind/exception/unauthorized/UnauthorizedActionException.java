package woongjin.gatherMind.exception.unauthorized;

public class UnauthorizedActionException extends UnauthorizedException {
    public UnauthorizedActionException(String message) {
        super(message);
    }

    public UnauthorizedActionException() {
        super("권한이 없습니다");
    }

}
