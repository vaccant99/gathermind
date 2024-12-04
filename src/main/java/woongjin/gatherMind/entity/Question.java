package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import woongjin.gatherMind.enums.QuestionOption;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Lob
    private String content;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private String title;
    private QuestionOption option;

    @ManyToOne
    @JoinColumn(name = "studyMemberId")
    private StudyMember studyMember;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<EntityFileMapping> EntityFileMappings;

}
