package woongjin.gatherMind.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import woongjin.gatherMind.enums.StudyStatus;

@Converter(autoApply = true)
public class StudyStatusConverter implements AttributeConverter<StudyStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StudyStatus studyStatus) {
        return (studyStatus == null) ? null : studyStatus.getCode();
    }

    @Override
    public StudyStatus convertToEntityAttribute(Integer integer) {
        if(integer == null) {
            return null;
        }
        return StudyStatus.fromCode(integer);

    }
}
