package woongjin.gatherMind.exception.notFound;

public class AnswerNotFoundException extends NotFoundException {

    public AnswerNotFoundException(String message) {
        super(message);
    }

    public AnswerNotFoundException (Long answerId) {
        super("Answer ID: " + answerId + " not found");
    }

    public AnswerNotFoundException() {
        super("Answer not found");
    }
}