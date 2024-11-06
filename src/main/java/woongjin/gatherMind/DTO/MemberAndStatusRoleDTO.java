package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MemberAndStatusRoleDTO {
    private String memberId;
    private String nickname;
    private String role;
    private String status;
}
