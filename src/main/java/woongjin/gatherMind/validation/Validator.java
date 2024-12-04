package woongjin.gatherMind.validation;

public interface Validator<T> {
    boolean isValid(T value);
    String getErrorMessage();

    // 각 Validator가 고유한 예외를 반환하도록 메서드 추가
    default RuntimeException getException() {
        return new IllegalArgumentException(getErrorMessage());
    }

    // 기본 메서드에서 고유 예외를 던지도록 수정
    default void validate(T value) {
        if (!isValid(value)) {
            throw getException();
        }
    }
}
