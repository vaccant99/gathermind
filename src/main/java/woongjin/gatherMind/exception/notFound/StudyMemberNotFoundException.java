package woongjin.gatherMind.exception.notFound;

public class StudyMemberNotFoundException extends NotFoundException {

    public StudyMemberNotFoundException(String message) { super(message); }

    public StudyMemberNotFoundException (Long studyMemberId) {
        super("StudyMember ID: " + studyMemberId + " not found");
    }
    public StudyMemberNotFoundException (String memberId, Long studyId) {
        super("StudyMember not found for Member ID : " + memberId + " and Study ID :" + studyId);
    }

    public StudyMemberNotFoundException() {
        super("StudyMember not found");
    }
}
