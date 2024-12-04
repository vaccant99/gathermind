package woongjin.gatherMind.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woongjin.gatherMind.constants.ErrorMessages;
import woongjin.gatherMind.exception.conflict.DuplicateNicknameException;
import woongjin.gatherMind.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class UniqueNicknameValidator implements Validator<String> {

    private final MemberRepository memberRepository;

    @Override
    public boolean isValid(String nickname) {
        return !memberRepository.existsByNickname(nickname); // 고유성 검증;
    }

    @Override
    public String getErrorMessage() {
        return ErrorMessages.DUPLICATE_NICKNAME;
    }

    @Override
    public RuntimeException getException() {
        return new DuplicateNicknameException(getErrorMessage());
    }
}
