package woongjin.gatherMind.exception.conflict;

public class DuplicateEmailException extends ConflictException{
    public DuplicateEmailException(String message) {
        super(message);
    }
}
