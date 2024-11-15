package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDTO {

    private Long questionId;
    private String option;
    private String content;
    private String title;
    private String memberId;
    private String studyTitle;
    private LocalDateTime createdAt;
    private List<AnswerDTO> answers;

    public QuestionDTO(Long questionId, String content, String title, String memberId, String studyTitle) {
        this.questionId = questionId;
        this.content = content;
        this.title = title;
        this.memberId = memberId;
        this.studyTitle = studyTitle;
    }
}
