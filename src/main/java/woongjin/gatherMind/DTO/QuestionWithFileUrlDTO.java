package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.enums.QuestionOption;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class QuestionWithFileUrlDTO {
    private Long questionId;
    private QuestionOption option;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String memberId;
    private String nickname;
    private String fileName;
    private String url;

    public QuestionWithFileUrlDTO(Question question, String fileName, String url) {
        this.questionId = question.getQuestionId();
        this.option = question.getOption();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.createdAt = question.getCreatedAt();
        this.memberId = question.getStudyMember().getMember().getMemberId();
        this.nickname = question.getStudyMember().getMember().getNickname();
        this.fileName = fileName;
        this.url = url;
    }
}