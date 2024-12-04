package woongjin.gatherMind.exception.notFound;

import woongjin.gatherMind.constants.ErrorMessages;

public class QuestionNotFoundException extends NotFoundException  {

    public QuestionNotFoundException(String message) {
        super(message);
    }

    public QuestionNotFoundException (Long questionId) {
        super("Question ID: " + questionId + " not found");
    }

    public QuestionNotFoundException() {
        super(ErrorMessages.QUESTION_NOT_FOUND);
    }
}
