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
public class AnswerDTOInQuestion {
    private Long answerId;
    private String content;
    private LocalDateTime createdAt;
    private String memberId;
    private String nickname;

    public AnswerDTOInQuestion(Answer answer) {
        this.answerId = answer.getAnswerId();
        this.content = answer.getContent();
        this.createdAt = answer.getCreatedAt();
        this.memberId = answer.getMemberId();
        this.nickname = answer.getMember().getNickname();
    }
}
