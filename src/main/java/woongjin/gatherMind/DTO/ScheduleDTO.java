package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ScheduleDTO {
    private Long scheduleId;
    private Long studyId;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private String location;
}
