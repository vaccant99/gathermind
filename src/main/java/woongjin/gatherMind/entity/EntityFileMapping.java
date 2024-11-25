package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityFileMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileMappingId;

    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "studyMemberId", insertable = false, updatable = false)
    private StudyMember studyMember;

    // FileMetadata와 1:1 관계
    @OneToOne
    @JoinColumn(name = "file_metadata_id", referencedColumnName = "fileId")
    private FileMetadata fileMetadata;
}
