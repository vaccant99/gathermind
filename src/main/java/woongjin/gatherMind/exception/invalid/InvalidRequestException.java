package woongjin.gatherMind.exception.invalid;

import woongjin.gatherMind.exception.BaseException;

public class InvalidRequestException extends BaseException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
