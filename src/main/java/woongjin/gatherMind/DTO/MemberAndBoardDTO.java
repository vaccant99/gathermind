package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;


@Data
@AllArgsConstructor
public class MemberAndBoardDTO {
    private List<MemberAndStatusRoleDTO> members;
    private Page<QuestionDTO> questions;
}
