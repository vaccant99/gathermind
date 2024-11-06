package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class GroupMembershipDTO {

    private long membershipId;
    private LocalDateTime arriveTime;
    private boolean isLate;
    private String memberId;
    private Long appointmentId;
    private Long meetingId;

}
