package woongjin.gatherMind.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class StudyMemberDTO {
    private Long studyMemberId;
    private String memberId;
    private Long studyId;
    private String role;
    private LocalDateTime joinDate;
    private String status;
    private String progress;
}