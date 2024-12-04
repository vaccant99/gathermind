package woongjin.gatherMind.exception.notFound;

import woongjin.gatherMind.constants.ErrorMessages;

public class AnswerNotFoundException extends NotFoundException {

    public AnswerNotFoundException(String message) {
        super(message);
    }

    public AnswerNotFoundException (Long answerId) {
        super("Answer ID: " + answerId + " not found");
    }

    public AnswerNotFoundException() {
        super(ErrorMessages.ANSWER_NOT_FOUND);
    }
}