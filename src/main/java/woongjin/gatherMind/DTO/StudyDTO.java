package woongjin.gatherMind.DTO;

import lombok.*;
import woongjin.gatherMind.entity.Study;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyDTO {
    private Long studyId;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;

    public StudyDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public StudyDTO(Study study) {
        this.studyId = study.getStudyId();
        this.title = study.getTitle();
        this.description = study.getDescription();
        this.createdAt = study.getCreatedAt();
        this.status = study.getStatus();
    }
}
