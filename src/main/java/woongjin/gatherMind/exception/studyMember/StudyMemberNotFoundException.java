package woongjin.gatherMind.exception.studyMember;

public class StudyMemberNotFoundException extends RuntimeException {

    public StudyMemberNotFoundException(String message) { super(message); }

    public StudyMemberNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }
}
