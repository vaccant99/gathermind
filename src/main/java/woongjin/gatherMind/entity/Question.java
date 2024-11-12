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
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private String title;
    private  String option;
    private String memberId;
    private Long studyId;


    @ManyToOne
    @JoinColumn(name = "studyMemberId")
    private StudyMember studyMember;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "memberId", insertable = false, updatable = false)
    private Member member; // Member와의 관계 설정

    @ManyToOne
    @JoinColumn(name = "studyId", insertable = false, updatable = false)
    private Study study;

}
