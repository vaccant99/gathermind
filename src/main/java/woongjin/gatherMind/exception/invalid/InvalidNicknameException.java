package woongjin.gatherMind.exception.invalid;

public class InvalidNicknameException extends RuntimeException{

    public InvalidNicknameException(String message) {
        super(message);
    }

    public InvalidNicknameException(String message, Throwable cause) {
        super(message,cause);
    }
}