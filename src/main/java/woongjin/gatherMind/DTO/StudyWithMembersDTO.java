package woongjin.gatherMind.DTO;

import lombok.*;
import org.springframework.data.domain.Page;
import woongjin.gatherMind.entity.Question;

import java.util.List;

@Data
@AllArgsConstructor
public class StudyWithMembersDTO {
    private Long studyId;
    private String title;
    private String description;
    private Long pendingCnt;
    private List<MemberAndStatusRoleDTO> members;
    private Page<QuestionWithoutAnswerDTO> questions;
}
