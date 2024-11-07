package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    private Long questionId;
    private Long studyId;
    private String content;
    private LocalDateTime createdAt;
    private String memberId;

    // Answer - Question (N:1)
    @ManyToOne
    @JoinColumn(name = "questionId", insertable = false, updatable = false)
    private Question question;

    // Answer - Member (N:1)
    @ManyToOne
    @JoinColumn(name = "memberId", insertable = false, updatable = false)
    private Member member;

    // Answer - Study (N:1)
    @ManyToOne
    @JoinColumn(name = "studyId", insertable = false, updatable = false)
    private Study study;
}
