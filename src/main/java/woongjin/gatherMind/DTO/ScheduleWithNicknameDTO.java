package woongjin.gatherMind.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ScheduleWithNicknameDTO {

    private Long scheduleId;
    private String title;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;
    private String nickname;
    private String location;


    public ScheduleWithNicknameDTO(ScheduleResponseDTO schedule, String nickname) {
        this.scheduleId = schedule.getScheduleId();
        this.title = schedule.getTitle();
        this.description = schedule.getDescription();
        this.dateTime = schedule.getDateTime();
        this.nickname = nickname;
        this.location = schedule.getLocation();
    }
}
