package woongjin.gatherMind.exception.question;

public class QuestionNotFoundException extends RuntimeException{

    public QuestionNotFoundException(String message) {
        super(message);
    }

    public QuestionNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }

    public QuestionNotFoundException (Long questionId) {
        super("Question ID: " + questionId + " not found");
    }

    public QuestionNotFoundException() {
        super("Question not found");
    }
}
