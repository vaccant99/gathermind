package woongjin.gatherMind.DTO;

import lombok.Getter;
import lombok.Setter;
import woongjin.gatherMind.entity.Study;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudyDTO {
    private Long studyId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private String status;

    // 기본 생성자 추가
    public StudyDTO() {
    }

    public StudyDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Study 객체를 인수로 받는 생성자도 유지
    public StudyDTO(Study study) {
        this.studyId = study.getStudyId();
        this.title = study.getTitle();
        this.description = study.getDescription();
        this.createdAt = study.getCreatedAt();
        this.status = study.getStatus();
    }
}
