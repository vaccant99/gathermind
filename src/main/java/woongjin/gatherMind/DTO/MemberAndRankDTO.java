package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MemberAndRankDTO {
    private List<MemberLateCountDTO> memberLateCountDTOS;
    private List<MemberDTO> members;
}
