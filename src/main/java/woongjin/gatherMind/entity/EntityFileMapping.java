package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityFileMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileMappingId;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private FileMetadata file;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
//    @JoinColumn(name = "study_member_id", nullable = true)
    @JoinColumn(name = "study_member_id", insertable = false, updatable = false)
    private StudyMember studyMember;
}
