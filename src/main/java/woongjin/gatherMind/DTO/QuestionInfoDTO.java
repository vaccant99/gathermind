package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class QuestionInfoDTO {
    private Long questionId;
    private String option;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String memberId;
    private String nickname;
}