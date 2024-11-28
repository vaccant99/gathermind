package woongjin.gatherMind.validation;

import woongjin.gatherMind.DTO.MemberDTO;
import woongjin.gatherMind.exception.conflict.DuplicateEmailException;
import woongjin.gatherMind.exception.conflict.DuplicateMemberIdException;
import woongjin.gatherMind.exception.conflict.DuplicateNicknameException;
import woongjin.gatherMind.repository.MemberRepository;

public class UniqueFieldValidator {

    public static void validateUniqueFields(MemberDTO memberDTO, MemberRepository memberRepository) {
        if (memberRepository.existsByMemberId(memberDTO.getMemberId())) {
            throw new DuplicateMemberIdException("이미 사용 중인 ID입니다.");
        }
        if (memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }
        if (memberRepository.existsByNickname(memberDTO.getNickname())) {
            throw new DuplicateNicknameException("이미 사용 중인 닉네임입니다.");
        }
    }
}
