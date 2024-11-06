package woongjin.gatherMind.exception.member;

public class StudyNotFoundException extends RuntimeException{

    public StudyNotFoundException(String message){
        super(message);
    }

    public StudyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
