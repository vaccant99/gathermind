package woongjin.gatherMind.enums;

import woongjin.gatherMind.exception.notFound.QuestionOptionCodeNotFoundException;

public enum QuestionOption {

    QUESTION(1),
    FILE_SHARED(2);

    private final int code;

    QuestionOption(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static QuestionOption fromCode(int code) {
        for (QuestionOption questionOption : QuestionOption.values()) {
            if (questionOption.code == code) {
                return questionOption;
            }
        }
        throw  new QuestionOptionCodeNotFoundException(code);
    }
}
