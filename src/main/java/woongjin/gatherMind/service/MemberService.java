package woongjin.gatherMind.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.*;

import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.exception.conflict.DuplicateEmailException;
import woongjin.gatherMind.exception.conflict.DuplicateMemberIdException;
import woongjin.gatherMind.exception.conflict.DuplicateNicknameException;
import woongjin.gatherMind.exception.invalid.InvalidNicknameException;
import woongjin.gatherMind.exception.invalid.InvalidPasswordException;
import woongjin.gatherMind.exception.notFound.MemberNotFoundException;
import woongjin.gatherMind.repository.MemberRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.auth.MemberDetails;

import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.repository.AnswerRepository;
import woongjin.gatherMind.validation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


import static woongjin.gatherMind.validation.UniqueFieldValidator.validateUniqueFields;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final CommonLookupService commonLookupService;

    private final MemberRepository memberRepository;
    private final AnswerRepository answerRepository;

    private final NicknameValidator  nicknameValidator;
    private final PasswordValidator  passwordValidator;
    private final UniqueNicknameValidator uniqueNicknameValidator;

    // 기본 프로필 이미지 URL
//    private static final String DEFAULT_PROFILE_IMAGE_URL = "https://yourdomain.com/images/default-profile.png";
    private static final String DEFAULT_PROFILE_IMAGE_URL = "api/files/default-profile";


    /**
     * 회원 정보 및 역할을 조회합니다.
     *
     * @param memberId 회원 ID
     * @param studyId  스터디 ID
     * @return 회원 정보와 역할이 담긴 DTO
     * @throws MemberNotFoundException 회원이 존재하지 않을 경우
     */
    public MemberAndStatusRoleDTO getMemberAndRoleByMemberId(String memberId, Long studyId) {
        return memberRepository.findMemberAndRoleByMemberId(memberId, studyId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    /**
     * 회원가입 처리
     *
     * @param memberDTO 회원가입 정보가 담긴 DTO
     * @throws DuplicateMemberIdException 이미 사용 중인 ID일 경우
     * @throws DuplicateEmailException    이미 사용 중인 이메일일 경우
     * @throws DuplicateNicknameException 이미 사용 중인 닉네임일 경우
     */
    @Transactional
    public void signup(MemberDTO memberDTO) {
        validateUniqueFields(memberDTO, memberRepository);

        Member member = new Member();
        member.setMemberId(memberDTO.getMemberId());
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setEmail(memberDTO.getEmail());
        member.setNickname(memberDTO.getNickname());
        member.setCreatedAt(LocalDateTime.now());
        member.setProfileImage(DEFAULT_PROFILE_IMAGE_URL);

        memberRepository.save(member);
    }

    /**
     * 로그인 처리 및 JWT 토큰 반환
     *
     * @param loginDTO 로그인 정보가 담긴 DTO
     * @return JWT 토큰
     * @throws MemberNotFoundException  회원 ID가 존재하지 않을 경우
     * @throws InvalidPasswordException 비밀번호가 틀린 경우
     */
    public String authenticate(LoginDTO loginDTO) {

        Member member = commonLookupService.findByMemberId(loginDTO.getMemberId());
        if (!passwordEncoder.matches(loginDTO.getPassword(), member.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 잘못되었습니다.");
        }
        return jwtTokenProvider.createToken(loginDTO.getMemberId());
    }

    /**
     * 회원 정보 조회
     *
     * @param memberId 조회할 회원의 ID
     * @return 회원 정보 DTO
     * @throws MemberNotFoundException 회원 ID가 존재하지 않을 경우
     */
    public MemberDTO getMember(String memberId) {
        Member member = commonLookupService.findByMemberId(memberId);
        return new MemberDTO(member);
    }


    /**
     * 회원 탈퇴
     *
     * @param memberId 탈퇴할 회원의 ID
     * @throws MemberNotFoundException 회원 ID가 존재하지 않을 경우
     */
    @Transactional
    public void deleteAccount(String memberId) {
        Member member = commonLookupService.findByMemberId(memberId);
        memberRepository.delete(member);
    }

    /**
     * 회원 정보 수정
     *
     * @param memberId    수정할 회원의 ID
     * @param newNickname 새로운 닉네임 (옵션)
     * @param newPassword 새로운 비밀번호 (옵션)
     * @return 수정 성공 메시지
     * @throws InvalidNicknameException 닉네임이 유효하지 않은 경우
     * @throws InvalidPasswordException 비밀번호가 유효하지 않은 경우
     */
    @Transactional
    public String updateMemberInfo(String memberId, String newNickname, String newPassword) {
        Member member = commonLookupService.findByMemberId(memberId);
        List<String> successMessages = new ArrayList<>();

        // 닉네임 변경
        validateAndUpdateField(member, newNickname, nicknameValidator, member::setNickname, true);
        if (newNickname != null && !newNickname.isEmpty() && !newNickname.equals(member.getNickname())) {
            successMessages.add(String.format("닉네임이 '%s'에서 '%s'으로 변경되었습니다.", member.getNickname(), newNickname));
        }

        validateAndUpdateField(member, newNickname, passwordValidator,
                password -> member.setPassword(passwordEncoder.encode(password)), false);
        if (newPassword != null && !newPassword.isEmpty()) {
            successMessages.add("비밀번호가 안전하게 변경되었습니다.");
        }

        memberRepository.save(member);
        return successMessages.isEmpty() ? "수정된 내용이 없습니다." : String.join("\n", successMessages);
    }

    /**
     * 최근 답글 조회
     *
     * @param memberId    조회할 회원의 ID
     * @return 조회한 답글 DTO
     */
    public List<AnswerDTO> findRecentAnswersByMemberId(String memberId) {
        return answerRepository.findTop3ByMemberIdOrderByCreatedAtDesc(memberId)
                .stream().map(AnswerDTO::new).toList();
    }

//    /**
//     * 회원 ID로 회원체크
//     *
//     * @param memberId 조회할 회원 ID
//     * @throws MemberNotFoundException 회원 ID가 존재하지 않을 경우
//     */
//    public void checkMemberExists(String memberId) {
//        if (!memberRepository.existsById(memberId)) {
//            throw new MemberNotFoundException(memberId);
//        }
//    }

    public String getNicknameById(String memberId) {
        return memberRepository.findById(memberId)
                .map(Member::getNickname)
                .orElseThrow(() -> new MemberNotFoundException("Member ID: " + memberId + " not found"));
    }


    public Member findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new MemberNotFoundException("User : " + nickname + " not found"));
    }

    /**
     * 검증 및 필드 업데이트 메서드
     *
     * @param member        수정할 회원 객체
     * @param newValue      새로운 값
     * @param validator     검증 로직을 담당하는 Validator
     * @param updateFunction 필드 업데이트를 수행하는 Consumer
     */
    private void validateAndUpdateField(Member member, String newValue, Validator<String> validator,
                                        Consumer<String> updateFunction, boolean checkUnique) {
        if (newValue != null && !newValue.isEmpty()) {
            // 유효성 검증
            validator.validate(newValue);

            // 고유성 검증 (필요한 경우)
            if (checkUnique){
                uniqueNicknameValidator.validate(newValue);
            }

            // 필드 업데이트
            updateFunction.accept(newValue);
        }
    }

    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("회원 ID를 찾을 수 없습니다: " + memberId));
        return new MemberDetails(member);
    }
}
