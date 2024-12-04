package woongjin.gatherMind.exception.notFound;

import woongjin.gatherMind.constants.ErrorMessages;

public class MemberNotFoundException extends NotFoundException {

    public MemberNotFoundException(String message) {
        super(message);
    }


    public MemberNotFoundException forMemberId(String memberId) {
      return new MemberNotFoundException(memberId + ErrorMessages.MEMBER_NOT_FOUND);
    }

    public MemberNotFoundException() {
        super(ErrorMessages.MEMBER_NOT_FOUND);
    }


}