package woongjin.gatherMind.exception.unauthorized;

import woongjin.gatherMind.exception.BaseException;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
