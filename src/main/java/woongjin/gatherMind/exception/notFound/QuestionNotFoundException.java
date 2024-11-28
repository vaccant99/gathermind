package woongjin.gatherMind.exception.notFound;

public class QuestionNotFoundException extends NotFoundException  {

    public QuestionNotFoundException(String message) {
        super(message);
    }

    public QuestionNotFoundException (Long questionId) {
        super("Question ID: " + questionId + " not found");
    }

    public QuestionNotFoundException() {
        super("Question not found");
    }
}
