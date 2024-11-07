package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {
    @Id
    private String memberId;

    private String nickname;
    private String email;
    private String password;
    private String profileImage;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // 회원 생성 시간 자동 설정
    }

    // Member - StudyMember (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<StudyMember> studyMembers;

    // Member - Question (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Question> questions;

    // Member - Answer (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Answer> answers;
}
