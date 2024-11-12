package woongjin.gatherMind.DTO;

import lombok.Getter;
import lombok.Setter;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.repository.QuestionRepository;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerDTO {
    private Long answerId;
    private Long questionId;
    private Long studyId;
    private String content;
    private LocalDateTime createdAt;
    private String memberId;
    private String questionTitle;
    private String studyTitle;

    public AnswerDTO() {}

    public AnswerDTO(Answer answer) {
        this.answerId = answer.getAnswerId();
        this.content = answer.getContent();
        this.questionTitle = answer.getQuestion().getTitle(); // Question의 title 참조
        this.studyTitle = answer.getQuestion().getStudyMember().getStudy().getTitle();
    }

    public AnswerDTO(Answer answer, QuestionRepository questionRepository) {
        this.studyTitle = questionRepository.findStudyTitleByQuestionId(answer.getQuestion().getQuestionId())
                .orElse("Unknown");
    }
}