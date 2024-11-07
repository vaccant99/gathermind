package woongjin.gatherMind.exception.study;

public class StudyNotFoundException extends RuntimeException{

    public StudyNotFoundException(String message){
        super(message);
    }

    public StudyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
