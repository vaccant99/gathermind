package woongjin.gatherMind.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import woongjin.gatherMind.enums.Role;

//@Converter(autoApply = true)가 설정되어 있다면, 해당 Role 타입 필드에 자동으로 적용됩니다.
@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return (role == null) ? null : role.getCode();
    }

    @Override
    public Role convertToEntityAttribute(Integer integer) {
        if (integer == null) {
            return null;
        }
        return Role.fromCode(integer);
    }

}
