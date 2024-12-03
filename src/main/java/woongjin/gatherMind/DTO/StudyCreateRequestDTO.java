package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import woongjin.gatherMind.enums.StudyStatus;

@Data
@AllArgsConstructor
@Builder
public class StudyCreateRequestDTO {
    private Long studyId;
    private String title;
    private String description;
    private StudyStatus status;
    private String memberId;
}
