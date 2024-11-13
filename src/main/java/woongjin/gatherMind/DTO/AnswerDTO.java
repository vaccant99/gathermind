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
    private String memberId;
    private String content;
    private LocalDateTime createdAt;
    private String questionTitle;
    private String studyTitle;
    private String nickname;

    // Add this constructor to match the query
    public AnswerDTO(Long answerId, String content, LocalDateTime createdAt, String memberId, String nickname) {
        this.answerId = answerId;
        this.content = content;
        this.createdAt = createdAt;
        this.memberId = memberId;
        this.nickname = nickname;
    }

    public AnswerDTO(Answer answer) {
        this.answerId = answer.getAnswerId();
        this.content = answer.getContent();
        this.questionTitle = answer.getQuestion().getTitle(); // Question의 title 참조
        this.studyTitle = answer.getQuestion().getStudyMember().getStudy().getTitle();
    }
}
