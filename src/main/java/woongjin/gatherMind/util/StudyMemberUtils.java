package woongjin.gatherMind.util;

import jakarta.servlet.UnavailableException;
import woongjin.gatherMind.constants.RoleConstants;
import woongjin.gatherMind.entity.StudyMember;

public class StudyMemberUtils {
    public static void checkAdminRole(StudyMember adminMember) throws UnavailableException {
        if (!adminMember.getRole().equals(RoleConstants.ADMIN)) {
            throw new UnavailableException("접근 제한됨 - 관리자가 아닙니다.");
        }
    }
}
