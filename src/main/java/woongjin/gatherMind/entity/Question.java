package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "question")
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private String memberId;
    private Long studyId;
    private String content;
    private LocalDateTime createdAt;
    private String title;
    private String option;

    // Question - Member (N:1)
    @ManyToOne
    @JoinColumn(name = "memberId", insertable = false, updatable = false)
    private Member member;

    // Question - Study (N:1)
    @ManyToOne
    @JoinColumn(name = "studyId", insertable = false, updatable = false)
    private Study study;

    // Question - Answer (1:N)
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;
}
