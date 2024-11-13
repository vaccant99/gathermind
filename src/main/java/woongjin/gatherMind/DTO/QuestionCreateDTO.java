package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class QuestionCreateDTO {
    private Long questionId;
    private String option;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    private List<AnswerDTO> answers;
}