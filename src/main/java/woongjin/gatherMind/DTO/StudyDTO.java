package woongjin.gatherMind.DTO;

import lombok.*;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.enums.StudyStatus;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyDTO {
    private Long studyId;
    private String title;
    private String description;
    private StudyStatus status;
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

    // 추가로 필요한 생성자
    public StudyDTO(Long studyId, String title, String description) {
        this.studyId = studyId;
        this.title = title;
        this.description = description;
    }

}
