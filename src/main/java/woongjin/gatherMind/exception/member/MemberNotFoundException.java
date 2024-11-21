package woongjin.gatherMind.exception.member;

public class MemberNotFoundException extends RuntimeException{

    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberNotFoundException forMemberId(String memberId) {
      return new MemberNotFoundException("Member ID: " + memberId + " not found");
    }

    public MemberNotFoundException() {
        super("Member not found");
    }


}