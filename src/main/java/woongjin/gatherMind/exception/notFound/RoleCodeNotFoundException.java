package woongjin.gatherMind.exception.notFound;

import woongjin.gatherMind.constants.ErrorMessages;

public class RoleCodeNotFoundException extends NotFoundException {

    public RoleCodeNotFoundException(String message) {
        super(message);
    }

    public RoleCodeNotFoundException(int code) {
        super(code+ErrorMessages.ROLE_CODE_NOT_FOUND);
    }

    public RoleCodeNotFoundException() {
        super(ErrorMessages.ROLE_CODE_NOT_FOUND);
    }
}