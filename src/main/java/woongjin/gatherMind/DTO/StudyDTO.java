package woongjin.gatherMind.DTO;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import woongjin.gatherMind.entity.Schedule;
import woongjin.gatherMind.entity.StudyMember;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class StudyDTO {

    private Long studyId;
    private String title;
    private String description;
    private String status;
}
