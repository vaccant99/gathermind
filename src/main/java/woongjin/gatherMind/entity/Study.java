package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import woongjin.gatherMind.DTO.StudyCreateRequestDTO;
import woongjin.gatherMind.enums.StudyStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;
    private String title;
    private String description;
    private StudyStatus status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyMember> studyMembers;

    // Study 엔티티로 변환하는 메서드
    public static Study createStudy(StudyCreateRequestDTO dto) {
        Study study = new Study();
        study.setStatus(dto.getStatus());
        study.setTitle(dto.getTitle());
        study.setDescription(dto.getDescription());
        return study;
    }

}
