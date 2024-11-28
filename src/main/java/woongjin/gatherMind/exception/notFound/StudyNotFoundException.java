package woongjin.gatherMind.exception.notFound;

public class StudyNotFoundException extends NotFoundException {

    public StudyNotFoundException(String message){
        super(message);
    }

    public StudyNotFoundException (Long studyId) {
        super("Study ID: " + studyId + " not found");
    }

    public StudyNotFoundException() {
        super("Study not found");
    }
}
