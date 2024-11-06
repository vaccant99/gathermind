package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class AppointmentAndAttendCheckDTO {

    private Long appointmentId;
    private String appointmentCreatedId;
    private String appointmentName;
    private Boolean appointmentStatus;
    private LocalDateTime appointmentTime;
    private LocalDateTime createdAt;
    private String location;
    private String penalty;
    private Boolean isAttend;
}
