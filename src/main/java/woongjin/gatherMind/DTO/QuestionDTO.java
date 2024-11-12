package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDTO {

    private Long questionId;
    private String content;
    private String title;
    private String memberId;
    private String studyTitle;
    private LocalDateTime createdAt;

}
