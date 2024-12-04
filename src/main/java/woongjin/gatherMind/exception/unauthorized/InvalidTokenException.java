package woongjin.gatherMind.exception.unauthorized;

import woongjin.gatherMind.exception.invalid.InvalidRequestException;

public class InvalidTokenException extends UnauthorizedException {

    public InvalidTokenException(String message) {
        super(message);
    }

}