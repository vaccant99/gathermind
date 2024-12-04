package woongjin.gatherMind.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import woongjin.gatherMind.entity.Schedule;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDTO {

    private Long scheduleId;
    private Long studyId;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private String location;

    public ScheduleDTO(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.title = schedule.getTitle();
        this.description = schedule.getDescription();
        this.dateTime = schedule.getDateTime();
        this.location = schedule.getLocation();
    }
}
