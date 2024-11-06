package woongjin.gatherMind.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionDTO {
    private Long questionId;
    private String memberId;
    private Long studyId;
    private String content;
    private LocalDateTime createdAt;
    private String title;
    private String option;
}
