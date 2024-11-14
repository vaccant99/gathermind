package woongjin.gatherMind.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private String memberId;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;



}