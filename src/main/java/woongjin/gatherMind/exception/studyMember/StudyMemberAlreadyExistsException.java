package woongjin.gatherMind.exception.studyMember;

import woongjin.gatherMind.constants.ErrorMessages;

public class StudyMemberAlreadyExistsException extends StudyMemberException {

    public StudyMemberAlreadyExistsException(String message) { super(message); }

    public StudyMemberAlreadyExistsException(Long studyMemberId) {
        super("(" + studyMemberId + ")" + ErrorMessages.STUDY_MEMBER_ALREADY_EXIST);
    }
    public StudyMemberAlreadyExistsException(String memberId, Long studyId) {
        super("( Member ID : " + memberId + " Study ID :" + studyId+")"+ErrorMessages.STUDY_MEMBER_ALREADY_EXIST);
    }

    public StudyMemberAlreadyExistsException() {
        super(ErrorMessages.STUDY_MEMBER_ALREADY_EXIST);
    }
}
