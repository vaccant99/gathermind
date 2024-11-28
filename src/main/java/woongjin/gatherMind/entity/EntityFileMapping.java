package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.*;
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
    @ToString.Exclude
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "studyMemberId", insertable = false, updatable = false)
    private StudyMember studyMember;

    // FileMetadata와 1:1 관계
    @OneToOne
    @ToString.Exclude
    @JoinColumn(name = "fileMetadataId", referencedColumnName = "fileMetadataId")
    private FileMetadata fileMetadata;
}
