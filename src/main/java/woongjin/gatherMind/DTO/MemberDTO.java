package woongjin.gatherMind.DTO;

import java.time.LocalDateTime;
import woongjin.gatherMind.entity.Member; // Member 엔티티 임포트

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

    // 모든 필드를 포함한 생성자
    public MemberDTO(String memberId, String nickname, String email, String profileImage, String password, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.password = password;
        this.createdAt = createdAt;
    }

    // password 없이 사용할 수 있는 생성자
    public MemberDTO(String memberId, String nickname, String email, String profileImage, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
    }

    // 기본 생성자
    public MemberDTO() {}

    // Getter와 Setter
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
