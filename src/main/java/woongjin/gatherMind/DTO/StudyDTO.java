package woongjin.gatherMind.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudyDTO {
    private Long studyId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private String status;
}
