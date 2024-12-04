package woongjin.gatherMind.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginDTO {
    private String memberId;
    private String password;
}