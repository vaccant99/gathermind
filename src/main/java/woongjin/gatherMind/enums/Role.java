package woongjin.gatherMind.enums;

import lombok.Getter;
import woongjin.gatherMind.exception.notFound.RoleCodeNotFoundException;

@Getter
public enum Role {
    ADMIN(1),
    MEMBER(2);

    private final int code;

    Role(int code) {
        this.code = code;
    }

    public static Role fromCode(int code) {
        for (Role role : Role.values()) {
            if (role.code == code) {
                return role;
            }
        }
        throw new RoleCodeNotFoundException(code);
    }
}
