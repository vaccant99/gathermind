package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import woongjin.gatherMind.entity.Member; // Member 엔티티 임포트

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String memberId;
    private String nickname;
    private String email;
    private String profileImage;
    private String password;
    private LocalDateTime createdAt;

    // Member 엔티티를 매개변수로 받는 생성자
    public MemberDTO(Member member) {
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.profileImage = member.getProfileImage();
        this.createdAt = member.getCreatedAt();
        // 필요에 따라 password 필드도 설정 가능
    }

    // password 없이 사용할 수 있는 생성자
    public MemberDTO(String memberId, String nickname, String email, String profileImage, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
    }

}
