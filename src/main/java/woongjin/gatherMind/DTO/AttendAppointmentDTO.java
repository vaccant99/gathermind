package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AttendAppointmentDTO {

    private Long membershipId;
    private String memberId;
    private Long meetingId;
    private Long appointmentId;

}
