package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class MemberAndBoardDTO {
    private List<MemberAndStatusRoleDTO> members;
    private List<QuestionDTO> questions;
}
