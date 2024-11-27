package woongjin.gatherMind.validation;

import org.springframework.stereotype.Component;
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
        return "비밀번호는 8자 이상 255자 이하로 입력해야 하며 공백을 포함할 수 없습니다.";
    }

    @Override
    public RuntimeException getException() {
        return new InvalidPasswordException(getErrorMessage());
    }
}
