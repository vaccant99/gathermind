package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import woongjin.gatherMind.enums.MemberStatus;
import woongjin.gatherMind.enums.Role;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MemberAndStatusRoleDTO {
    private String memberId;
    private String nickname;
    private Role role;
    private MemberStatus status;
    private Long studyMemberId;
}
