package woongjin.gatherMind.exception.study;

public class StudyNotFoundException extends RuntimeException{

    public StudyNotFoundException(String message){
        super(message);
    }

    public StudyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudyNotFoundException (Long studyId) {
        super("Study ID: " + studyId + " not found");
    }

    public StudyNotFoundException() {
        super("Study not found");
    }
}
