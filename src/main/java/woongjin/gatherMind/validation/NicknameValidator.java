package woongjin.gatherMind.validation;

import org.springframework.stereotype.Component;
import woongjin.gatherMind.constants.ErrorMessages;
import woongjin.gatherMind.exception.invalid.InvalidNicknameException;

@Component
public class NicknameValidator implements Validator<String>{

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 20;
    private static final String NICKNAME_PATTERN = "^[a-zA-Z0-9가-힣]+$";

    // 정적 메서드로 유효성 검사 수행
    public boolean isValid(String nickname) {
        return nickname != null
                && nickname.length() >= MIN_LENGTH
                && nickname.length() <= MAX_LENGTH
                && nickname.matches(NICKNAME_PATTERN);
    }

    @Override
    public String getErrorMessage() {
        return ErrorMessages.INVALID_NICKNAME;
    }

    @Override
    public RuntimeException getException() {
        return new InvalidNicknameException(getErrorMessage());
    }
}
