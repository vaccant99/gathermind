package woongjin.gatherMind.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import woongjin.gatherMind.enums.QuestionOption;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class QuestionWithoutAnswerDTO {

    private Long questionId;
    private String content;
    private String title;
    private QuestionOption option;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

}
