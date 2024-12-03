package woongjin.gatherMind.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import woongjin.gatherMind.enums.QuestionOption;

@Converter(autoApply = true)
public class QuestionOptionConverter implements AttributeConverter<QuestionOption, Integer> {
    @Override
    public Integer convertToDatabaseColumn(QuestionOption questionOption) {
        return questionOption == null ? null : questionOption.getCode();
    }

    @Override
    public QuestionOption convertToEntityAttribute(Integer integer) {
        if(integer == null) {
            return null;
        }
        return QuestionOption.fromCode(integer);
    }
}
