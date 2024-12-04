package woongjin.gatherMind.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.enums.MemberStatus;
import woongjin.gatherMind.enums.Role;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class StudyMemberDTO {
    private Long studyMemberId;
    private String memberId;
    private Long studyId;
    private Role role;
    private LocalDateTime joinDate;
    private MemberStatus memberStatus;
    private String progress;

    public StudyMemberDTO(StudyMember studyMember) {
        this.studyMemberId = studyMember.getStudyMemberId();
        this.memberId = studyMember.getMember().getMemberId();
        this.studyId = studyMember.getStudy().getStudyId();
        this.role = studyMember.getRole();
        this.joinDate = studyMember.getJoinedDate();
        this.memberStatus = studyMember.getStatus();
        this.progress = studyMember.getProgress();
    }
}