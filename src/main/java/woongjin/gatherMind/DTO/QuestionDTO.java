package woongjin.gatherMind.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.entity.StudyMember;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class QuestionDTO {


    private Long questionId;
    private String content;
    private String title;
    private  String option;
    private LocalDateTime createdAt;
}
