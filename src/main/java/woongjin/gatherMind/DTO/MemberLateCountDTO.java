package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MemberLateCountDTO {

    private String memberId;
    private String nickname;
    private Long appointmentId;
    private int lateCount;
    private int lateTime;

}
