package woongjin.gatherMind.exception.answer;

import woongjin.gatherMind.exception.member.MemberNotFoundException;

public class AnswerNotFoundException extends RuntimeException{

    public AnswerNotFoundException(String message) {
        super(message);
    }

    public AnswerNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }


    public AnswerNotFoundException (Long answerId) {
        super("Answer ID: " + answerId + " not found");
    }

    public AnswerNotFoundException() {
        super("Answer not found");
    }
}