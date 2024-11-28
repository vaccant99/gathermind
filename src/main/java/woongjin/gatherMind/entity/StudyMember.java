package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.*;
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
public class StudyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyMemberId;
    private String role;
    private String status;
    private String progress;

    @CreatedDate
    private LocalDateTime joinedDate;
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "studyId")
    private Study study;

    @OneToMany(mappedBy = "studyMember", cascade = CascadeType.ALL)
    private List<Question> question;


    /**
     * 스터디 멤버 생성
     *
     * @param study    스터디
     * @param member   멤버
     * @param role     역할
     * @param status   상태
     * @param progress 진행도
     * @return 생성된 스터디 멤버
     */
    public static StudyMember createStudyMember(Study study, Member member, String role, String status, String progress) {
        StudyMember studyMember = new StudyMember();
        studyMember.setRole(role);
        studyMember.setStatus(status);
        studyMember.setProgress(progress);
        studyMember.setStudy(study);
        studyMember.setMember(member);
        return studyMember;
    }
}
