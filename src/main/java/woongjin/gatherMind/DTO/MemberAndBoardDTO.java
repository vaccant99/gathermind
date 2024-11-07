package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;


@Data
@AllArgsConstructor
public class MemberAndBoardDTO {
    private List<MemberAndStatusRoleDTO> members;
    private List<QuestionDTO> questions;
}
