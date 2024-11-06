package woongjin.gatherMind.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class MemberDTO {
    private String memberId;
    private String nickname;
}
