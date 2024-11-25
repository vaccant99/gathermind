package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import woongjin.gatherMind.entity.Question;

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

    // 매개변수 생성자
    public QuestionDTO(Long questionId, String title, String content) {
        this.questionId = questionId;
        this.title = title;
        this.content = content;
    }

    // Question 엔티티를 기반으로 하는 생성자
    public QuestionDTO(Question question) {
        this.questionId = question.getQuestionId();
        this.title = question.getTitle();
        this.content = question.getContent();
    }
}
