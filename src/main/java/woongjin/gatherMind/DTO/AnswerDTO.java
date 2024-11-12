package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import woongjin.gatherMind.entity.Answer;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDTO {
    private Long answerId;
    private Long questionId;
    private Long studyId;
    private String content;
    private LocalDateTime createdAt;
    private String memberId;
    private String questionTitle;
    private String studyTitle;

    public AnswerDTO(Answer answer) {
        this.answerId = answer.getAnswerId();
        this.content = answer.getContent();
        this.questionTitle = answer.getQuestion().getTitle(); // Question의 title 참조
        this.studyTitle = answer.getQuestion().getStudyMember().getStudy().getTitle();
    }
}
