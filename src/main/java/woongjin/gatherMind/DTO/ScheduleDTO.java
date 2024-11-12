package woongjin.gatherMind.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleDTO {
    private Long scheduleId;
    private Long studyId;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private String location;
}