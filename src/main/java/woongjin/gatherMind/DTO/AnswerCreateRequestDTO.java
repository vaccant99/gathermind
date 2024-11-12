package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AnswerCreateRequestDTO {
    private Long answerId;
    private String memberId;
    private Long questionId;
    private String content;
}
