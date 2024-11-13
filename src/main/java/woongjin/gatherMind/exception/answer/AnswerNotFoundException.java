package woongjin.gatherMind.exception.answer;

public class AnswerNotFoundException extends RuntimeException{

    public AnswerNotFoundException(String message) {
        super(message);
    }

    public AnswerNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }
}