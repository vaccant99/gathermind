package woongjin.gatherMind.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import woongjin.gatherMind.enums.MemberStatus;

@Converter(autoApply = true)
public class StatusConverter  implements AttributeConverter<MemberStatus, Integer> {


    @Override
    public Integer convertToDatabaseColumn(MemberStatus memberStatus) {
        return (memberStatus) == null ? null : memberStatus.getCode();
    }

    @Override
    public MemberStatus convertToEntityAttribute(Integer integer) {
        if(integer == null) {
            return null;
        }
        return MemberStatus.fromCode(integer);
    }
}
