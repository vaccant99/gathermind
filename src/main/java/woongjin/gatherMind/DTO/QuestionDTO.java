package woongjin.gatherMind.DTO;

import lombok.Getter;
import lombok.Setter;
import woongjin.gatherMind.entity.Question;

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
    private String studyTitle;

    // 기본 생성자가 필요한 경우 추가
    public QuestionDTO() {}

    // Question 객체를 파라미터로 받는 생성자
    public QuestionDTO(Question question) {
        this.questionId = question.getQuestionId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.studyTitle = question.getStudy().getTitle();  // Study의 title 참조
    }
}
