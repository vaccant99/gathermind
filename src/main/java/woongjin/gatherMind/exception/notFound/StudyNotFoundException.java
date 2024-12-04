package woongjin.gatherMind.exception.notFound;

import woongjin.gatherMind.constants.ErrorMessages;

public class StudyNotFoundException extends NotFoundException {

    public StudyNotFoundException(String message){
        super(message);
    }

    public StudyNotFoundException (Long studyId) {
        super("Study ID: " + studyId + " not found");
    }

    public StudyNotFoundException() {
        super(ErrorMessages.STUDY_NOT_FOUND);
    }
}
