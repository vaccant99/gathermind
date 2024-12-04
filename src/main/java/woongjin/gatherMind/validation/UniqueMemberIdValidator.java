package woongjin.gatherMind.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woongjin.gatherMind.constants.ErrorMessages;
import woongjin.gatherMind.exception.conflict.DuplicateMemberIdException;
import woongjin.gatherMind.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class UniqueMemberIdValidator implements Validator<String> {

    private final MemberRepository memberRepository;

    @Override
    public boolean isValid(String memberId) {
        return !memberRepository.existsByMemberId(memberId);
    }

    @Override
    public String getErrorMessage() {
        return ErrorMessages.DUPLICATE_MEMBER_ID;
    }

    @Override
    public RuntimeException getException() {
        return new DuplicateMemberIdException(getErrorMessage());
    }
}
