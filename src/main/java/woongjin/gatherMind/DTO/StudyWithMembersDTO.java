package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import woongjin.gatherMind.entity.Question;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StudyWithMembersDTO {
    private Long studyId;
    private String title;
    private String description;
    private List<MemberAndStatusRoleDTO> members;
    private List<QuestionDTO> questions;
}
