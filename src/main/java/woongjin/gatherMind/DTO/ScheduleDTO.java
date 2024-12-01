package woongjin.gatherMind.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
