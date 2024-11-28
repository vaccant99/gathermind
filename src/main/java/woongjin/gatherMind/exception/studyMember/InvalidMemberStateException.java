package woongjin.gatherMind.exception.studyMember;

import woongjin.gatherMind.constants.ErrorMessages;
import woongjin.gatherMind.exception.BaseException;
import woongjin.gatherMind.exception.invalid.InvalidRequestException;

public class InvalidMemberStateException extends StudyMemberException {

    public InvalidMemberStateException(String message) {
        super(message);
    }


    public InvalidMemberStateException forMemberId(String memberId) {
      return new InvalidMemberStateException("(" + memberId + ")"+ErrorMessages.MEMBER_ALREADY_APPROVED);
    }

    public InvalidMemberStateException() {
        super(ErrorMessages.MEMBER_ALREADY_APPROVED);
    }


}