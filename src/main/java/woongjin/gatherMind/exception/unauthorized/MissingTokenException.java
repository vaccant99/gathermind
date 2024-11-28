package woongjin.gatherMind.exception.unauthorized;

public class MissingTokenException extends UnauthorizedException{

    public MissingTokenException(String message) {
        super(message);
    }
}