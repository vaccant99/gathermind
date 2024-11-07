package woongjin.gatherMind.DTO;

import lombok.*;
import woongjin.gatherMind.entity.Question;

import java.util.List;

@Data
@AllArgsConstructor
public class StudyWithMembersDTO {
    private Long studyId;
    private String title;
    private String description;
    private List<MemberAndStatusRoleDTO> members;
    private List<QuestionDTO> questions;
}
