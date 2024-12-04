package woongjin.gatherMind.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woongjin.gatherMind.constants.ErrorMessages;
import woongjin.gatherMind.exception.conflict.DuplicateEmailException;
import woongjin.gatherMind.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements Validator<String> {

    private final MemberRepository memberRepository;

    @Override
    public boolean isValid(String memberId) {
        return !memberRepository.existsByEmail(memberId);
    }

    @Override
    public String getErrorMessage() {
        return ErrorMessages.DUPLICATE_EMAIL;
    }

    @Override
    public RuntimeException getException() {
        return new DuplicateEmailException(getErrorMessage());
    }
}
