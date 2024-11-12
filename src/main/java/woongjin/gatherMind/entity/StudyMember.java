package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyMemberId;

    private String role;
    private String status;
    private String progress;

    private LocalDate joinDate;

    // StudyMember - Member (N:1)
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    // StudyMember - Study (N:1)
    @ManyToOne
    @JoinColumn(name = "studyId")
    private Study study;
}
