package woongjin.gatherMind.DTO;


import io.swagger.v3.oas.annotations.info.Info;
import lombok.*;
import woongjin.gatherMind.entity.Study;

import java.time.LocalDateTime;
@Builder
@Data
@AllArgsConstructor
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

    // 추가로 필요한 생성자
    public StudyDTO(Long studyId, String title, String description) {
        this.studyId = studyId;
        this.title = title;
        this.description = description;
    }
}
