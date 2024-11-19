package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyMemberConfirmDTO {

    private Long studyId;
    private String memberId;

}
