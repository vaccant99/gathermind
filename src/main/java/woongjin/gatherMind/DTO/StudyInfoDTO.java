package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class StudyInfoDTO {

    private Long studyId;
    private String title;
    private String description;
    private String status;
}
