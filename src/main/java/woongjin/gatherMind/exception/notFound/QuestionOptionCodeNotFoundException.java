package woongjin.gatherMind.exception.notFound;

import woongjin.gatherMind.constants.ErrorMessages;

public class QuestionOptionCodeNotFoundException extends NotFoundException {

    public QuestionOptionCodeNotFoundException(String message) {
        super(message);
    }

    public QuestionOptionCodeNotFoundException(int code) {
        super(code+ErrorMessages.QUESTION_OPTION_CODE_NOT_FOUND);
    }

    public QuestionOptionCodeNotFoundException() {
        super(ErrorMessages.QUESTION_OPTION_CODE_NOT_FOUND);
    }
}