package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
