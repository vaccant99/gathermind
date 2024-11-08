package woongjin.gatherMind.DTO;

import lombok.Getter;
import lombok.Setter;
import woongjin.gatherMind.entity.Answer;

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

    // 기본 생성자
    public AnswerDTO() {}

    // Answer 엔티티를 기반으로 생성하는 생성자 추가
    public AnswerDTO(Answer answer) {
        this.answerId = answer.getAnswerId();
        this.content = answer.getContent();
        this.questionTitle = answer.getQuestion().getTitle(); // Question의 title 참조
        this.studyTitle = answer.getQuestion().getStudy().getTitle();
    }
}
