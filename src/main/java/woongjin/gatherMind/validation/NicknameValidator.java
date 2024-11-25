package woongjin.gatherMind.validation;

public class NicknameValidator {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 20;
    private static final String NICKNAME_PATTERN = "^[a-zA-Z0-9가-힣]+$";

    // 정적 메서드로 유효성 검사 수행
    public static boolean isValid(String nickname) {
        return nickname != null
                && nickname.length() >= MIN_LENGTH
                && nickname.length() <= MAX_LENGTH
                && nickname.matches(NICKNAME_PATTERN);
    }
}
