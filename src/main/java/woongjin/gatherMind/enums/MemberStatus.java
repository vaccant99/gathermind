package woongjin.gatherMind.enums;


import woongjin.gatherMind.exception.notFound.StatusCodeNotFoundException;

public enum MemberStatus {

    PENDING(1),
    APPROVED(2);

    private final int code;

    MemberStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MemberStatus fromCode(int code){
        for(MemberStatus memberStatus : MemberStatus.values()) {
            if(memberStatus.code == code) {
                return memberStatus;
            }
        }
        throw new StatusCodeNotFoundException(code);
    }
}
