package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private String content;
    private String title;
    private String option;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Question - StudyMember (N:1) 관계를 통해 간접적으로 Member와 Study에 접근
    @ManyToOne
    @JoinColumn(name = "studyMemberId", insertable = false, updatable = false)
    private StudyMember studyMember;

    // Question - Answer (1:N)
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member; // Member와의 관계 설정

    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;
}
