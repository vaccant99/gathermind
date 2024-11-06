package woongjin.gatherMind.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class AppointmentDTO {

    private Long appointmentId;
    private String appointmentName;
    private LocalDateTime appointmentTime;
    private String location;
    private Boolean appointmentStatus;
    private String appointmentCreatedId;
    private String penalty;
}
