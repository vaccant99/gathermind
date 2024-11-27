package woongjin.gatherMind.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woongjin.gatherMind.entity.StudyMember;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class StudyMemberDTO {
    private Long studyMemberId;
    private String memberId;
    private Long studyId;
    private String role;
    private LocalDateTime joinDate;
    private String status;
    private String progress;

    public StudyMemberDTO(StudyMember studyMember) {
        this.studyMemberId = studyMember.getStudyMemberId();
        this.memberId = studyMember.getMember().getMemberId();
        this.studyId = studyMember.getStudy().getStudyId();
        this.role = studyMember.getRole();
        this.joinDate = studyMember.getJoinedDate();
        this.status = studyMember.getStatus();
        this.progress = studyMember.getProgress();
    }
}