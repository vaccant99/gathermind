package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;

    private String title;
    private String description;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private String status;

    // Study - StudyMember (1:N)
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyMember> studyMembers;
}
