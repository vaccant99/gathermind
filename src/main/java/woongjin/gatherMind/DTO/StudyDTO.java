package woongjin.gatherMind.DTO;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woongjin.gatherMind.entity.Study;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class StudyDTO {
    private Long studyId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private String status;


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
