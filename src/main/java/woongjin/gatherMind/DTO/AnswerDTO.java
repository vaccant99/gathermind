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
    private String nickname;

    public AnswerDTO(Answer answer) {
        this.answerId = answer.getAnswerId();
        this.content = answer.getContent();
        this.createdAt = answer.getCreatedAt();

        // Answer 엔티티에서 Question, Study, Member와의 관계가 제대로 매핑되어 있는지 체크
        if (answer.getQuestion() != null) {
            this.questionId = answer.getQuestion().getQuestionId();
            this.questionTitle = answer.getQuestion().getTitle();

            // Study 정보 참조
            if (answer.getQuestion().getStudyMember() != null &&
                    answer.getQuestion().getStudyMember().getStudy() != null) {
                this.studyId = answer.getQuestion().getStudyMember().getStudy().getStudyId();
                this.studyTitle = answer.getQuestion().getStudyMember().getStudy().getTitle();
            }
        }

        // Member 정보 참조
        if (answer.getMember() != null) {
            this.memberId = answer.getMember().getMemberId();
        }
    }

    // 쿼리에서 사용하는 필드를 포함한 생성자 추가
    public AnswerDTO(Long answerId, String content, LocalDateTime createdAt, String nickname) {
        this.answerId = answerId;
        this.content = content;
        this.createdAt = createdAt;
        this.nickname = nickname;
    }
}
