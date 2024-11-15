package woongjin.gatherMind.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import woongjin.gatherMind.entity.Member;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    @NotBlank
    private String memberId;

    @Size(min = 2, max = 20)
    private String nickname;

    @Email
    private String email;

    private String profileImage;

    private LocalDateTime createdAt;

    @Size(min = 8, max = 255, message = "비밀번호는 8자 이상 255자 이하로 입력해야 합니다.")
    private String password;

    // 모든 필드를 포함하는 생성자

    public MemberDTO(String memberId, String nickname, String email, String profileImage, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
    }

    // Member 엔티티를 매개변수로 받는 생성자 (password는 포함하지 않음)
    public MemberDTO(Member member) {
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.profileImage = member.getProfileImage();
        this.createdAt = member.getCreatedAt();
    }
}
