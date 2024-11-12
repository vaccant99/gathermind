package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    private Long questionId;
    private Long studyId;
    private String content;
    private String memberId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

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
