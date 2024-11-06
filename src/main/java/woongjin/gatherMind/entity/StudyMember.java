package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "study_member")
@Getter
@Setter
public class StudyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyMemberId;

    private Long userId;
    private Long studyId;
    private String role;
    private LocalDateTime joinDate;
    private String status;
    private String progress;

    // StudyMember - Member (N:1)
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // StudyMember - Study (N:1)
    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;
}
