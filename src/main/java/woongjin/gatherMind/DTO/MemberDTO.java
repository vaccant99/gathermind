package woongjin.gatherMind.DTO;

import lombok.Getter;
import lombok.Setter;
import woongjin.gatherMind.entity.Member;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDTO {
    private String memberId;
    private String nickname;
    private String email;
    private String password; // 회원가입 시에만 사용
    private String profileImage;
    private LocalDateTime createdAt;

    public MemberDTO() {}

    // Member 객체로부터 DTO를 생성하는 생성자
    public MemberDTO(Member member) {
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.password = member.getPassword();
    }
}
