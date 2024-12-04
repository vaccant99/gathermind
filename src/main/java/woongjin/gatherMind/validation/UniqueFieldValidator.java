package woongjin.gatherMind.validation;


import woongjin.gatherMind.DTO.MemberDTO;
import woongjin.gatherMind.constants.ErrorMessages;
import woongjin.gatherMind.exception.conflict.DuplicateEmailException;
import woongjin.gatherMind.exception.conflict.DuplicateMemberIdException;
import woongjin.gatherMind.exception.conflict.DuplicateNicknameException;
import woongjin.gatherMind.repository.MemberRepository;

public class UniqueFieldValidator {

    public static void validateUniqueFields(MemberDTO memberDTO, MemberRepository memberRepository) {

        if (memberRepository.existsByMemberId(memberDTO.getMemberId())) {
            throw new DuplicateMemberIdException(ErrorMessages.DUPLICATE_MEMBER_ID);
        }
        if (memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new DuplicateEmailException(ErrorMessages.DUPLICATE_EMAIL);
        }
        if (memberRepository.existsByNickname(memberDTO.getNickname())) {
            throw new DuplicateNicknameException(ErrorMessages.DUPLICATE_NICKNAME);
        }
    }
}
