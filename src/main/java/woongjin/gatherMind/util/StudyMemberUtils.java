package woongjin.gatherMind.util;

import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.enums.Role;
import woongjin.gatherMind.exception.unauthorized.UnauthorizedActionException;

public class StudyMemberUtils {
    /**
     * 관리자 권한 확인
     *
     * @param adminMember 관리자 스터디 멤버
     * @throws UnauthorizedActionException 관리자가 아닌 경우
     */
    public static void checkAdminRole(StudyMember adminMember) {
        if (Role.ADMIN != adminMember.getRole()) {
            throw new UnauthorizedActionException("접근 제한됨 - 관리자가 아닙니다.");
        }
    }
}
