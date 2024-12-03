package woongjin.gatherMind.exception.notFound;

import woongjin.gatherMind.constants.ErrorMessages;

public class StudyStatusCodeNotFoundException extends NotFoundException {

    public StudyStatusCodeNotFoundException(String message) {
        super(message);
    }

    public StudyStatusCodeNotFoundException(int code) {
        super(code+ErrorMessages.STATUS_CODE_NOT_FOUND);
    }

    public StudyStatusCodeNotFoundException() {
        super(ErrorMessages.STATUS_CODE_NOT_FOUND);
    }
}