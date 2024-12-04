package woongjin.gatherMind.exception.notFound;

import woongjin.gatherMind.constants.ErrorMessages;

public class StatusCodeNotFoundException extends NotFoundException {

    public StatusCodeNotFoundException(String message) {
        super(message);
    }

    public StatusCodeNotFoundException(int code) {
        super(code+ErrorMessages.STATUS_CODE_NOT_FOUND);
    }

    public StatusCodeNotFoundException() {
        super(ErrorMessages.STATUS_CODE_NOT_FOUND);
    }
}