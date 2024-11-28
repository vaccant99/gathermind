package woongjin.gatherMind.validation;

import org.springframework.stereotype.Component;
import woongjin.gatherMind.constants.ErrorMessages;
import woongjin.gatherMind.exception.invalid.InvalidPasswordException;

@Component
public class PasswordValidator implements Validator<String>  {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 255;

    public boolean isValid(String password) {
        return password != null
                && password.length() >= MIN_LENGTH
                && password.length() <= MAX_LENGTH
                && !password.contains(" ");
    }

    @Override
    public String getErrorMessage() {
        return ErrorMessages.INVALID_PASSWORD;
    }

    @Override
    public RuntimeException getException() {
        return new InvalidPasswordException(getErrorMessage());
    }
}
