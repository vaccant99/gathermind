package woongjin.gatherMind.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.DTO.StudyDTO;
import woongjin.gatherMind.DTO.StudyMemberConfirmDTO;
import woongjin.gatherMind.DTO.StudyMemberDTO;
import woongjin.gatherMind.constants.ProgressConstants;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.enums.MemberStatus;
import woongjin.gatherMind.enums.Role;
import woongjin.gatherMind.enums.StudyStatus;
import woongjin.gatherMind.exception.invalid.InvalidStudyStatusException;
import woongjin.gatherMind.exception.unauthorized.UnauthorizedActionException;
import woongjin.gatherMind.exception.studyMember.InvalidMemberStateException;
import woongjin.gatherMind.exception.studyMember.StudyMemberAlreadyExistsException;
import woongjin.gatherMind.repository.StudyMemberRepository;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.repository.StudyRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static woongjin.gatherMind.util.StudyMemberUtils.checkAdminRole;

@Service
@RequiredArgsConstructor
public class StudyMemberService {

    private final StudyMemberRepository studyMemberRepository;
    private final StudyRepository studyRepository;

    private final CommonLookupService commonLookupService;

    /**
     * 스터디 지원
     *
     * @param memberId 지원하는 회원 ID
     * @param studyId  지원하는 스터디 ID
     * @return 지원된 스터디 멤버 DTO
     * @throws StudyMemberAlreadyExistsException 이미 가입한 멤버일 경우
     */
    @Transactional
    public StudyMemberDTO applyStudy(String memberId, Long studyId) {

        Member member = commonLookupService.findByMemberId(memberId);

        Study study = commonLookupService.findStudyByStudyId(studyId);

        // 저장하기전 있는지 체크
        if (studyMemberRepository.existsByMember_MemberIdAndStudy_StudyId(memberId, studyId)) {
            throw new StudyMemberAlreadyExistsException(memberId, studyId);
        }

        if(study.getStatus() == StudyStatus.CLOSED) {
            throw new InvalidStudyStatusException("스터디가 모집 중이 아닙니다.");
        }

        StudyMember studyMember = new StudyMember();
        studyMember.setRole(Role.MEMBER);
        studyMember.setStatus(MemberStatus.PENDING);
        studyMember.setMember(member);
        studyMember.setStudy(study);
        studyMember.setProgress(ProgressConstants.NOT_STARTED);

        return new StudyMemberDTO(studyMemberRepository.save(studyMember));
    }

    /**
     * 스터디 지원 승인
     *
     * @param adminId 관리자의 회원 ID
     * @param dto     승인할 멤버 정보
     * @return 승인된 스터디 멤버 DTO
     * @throws UnauthorizedActionException 관리자가 승인 권한이 없을 경우
     * @throws InvalidMemberStateException 멤버 상태가 PENDING이 아닌 경우
     */
    @Transactional
    public StudyMemberDTO confirmStudyMember(String adminId, StudyMemberConfirmDTO dto) {

        String memberId = dto.getMemberId();
        Long studyId = dto.getStudyId();

        // 관리자가 해당 스터디의 관리자 권한이 있는지 확인
        StudyMember adminMember = commonLookupService.findByMemberIdAndStudyId(adminId, studyId);

        checkAdminRole(adminMember);  // 관리 권한 체크 메서드 호출

        // 승인할 멤버와 스터디, StudyMember 객체 가져오기
        commonLookupService.checkMemberExists(memberId);
        commonLookupService.findStudyByStudyId(studyId);

        // 승인 받을 멤버 정보
        StudyMember studyMember = commonLookupService.findByMemberIdAndStudyId(memberId, studyId);

        if ( studyMember.getStatus() != MemberStatus.PENDING) {
            throw new InvalidMemberStateException(memberId);
        }

        studyMember.setStatus(MemberStatus.APPROVED);
        return new StudyMemberDTO(studyMemberRepository.save(studyMember));

    }

    /**
     * 스터디 멤버 삭제
     *
     * @param adminId 관리자의 회원 ID
     * @param dto     강퇴할 멤버 정보
     * @return 강퇴된 스터디 멤버 DTO
     * @throws UnauthorizedActionException 관리자가 승인 권한이 없을 경우
     * @throws InvalidMemberStateException 멤버 상태가 PENDING이 아닌 경우
     */
    @Transactional
    public void resignStudyMember(String adminId, StudyMemberConfirmDTO dto) {

        String memberId = dto.getMemberId();
        Long studyId = dto.getStudyId();

        // 관리자가 해당 스터디의 관리자 권한이 있는지 확인
        StudyMember adminMember = commonLookupService.findByMemberIdAndStudyId(adminId, studyId);
        checkAdminRole(adminMember);  // 관리 권한 체크 메서드 호출

        // 강퇴할 멤버와 스터디, StudyMember 객체 가져오기
        commonLookupService.checkMemberExists(memberId);
        commonLookupService.findStudyByStudyId(studyId);

        // 강퇴당할 멤버 정보
        StudyMember studyMember = commonLookupService.findByMemberIdAndStudyId(memberId, studyId);

        if ( studyMember.getStatus() != MemberStatus.APPROVED) {
            throw new InvalidMemberStateException(memberId);
        }

        studyMemberRepository.delete(studyMember);
    }

    /**
     * 회원 ID로 가입한 스터디 목록 조회
     *
     * @param memberId 회원 ID
     * @return 가입한 스터디 목록
     */
    public List<StudyDTO> findStudiesByMemberId(String memberId) {

        return studyRepository.findByStudyMembers_Member_MemberId(memberId).stream()
                .map(StudyDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 회원 ID로 승인된 스터디 목록 조회
     *
     * @param memberId 회원 ID
     * @return 승인된 스터디 목록
     */
    public List<StudyDTO> findApprovedStudiesByMemberId(String memberId) {
        return studyRepository.findByStudyMembers_Member_MemberIdAndStudyMembers_Status(memberId, MemberStatus.APPROVED)
                .stream().map(StudyDTO::new)
                .collect(Collectors.toList());

//        return studyMemberRepository.findStudyIdsByMemberIdAndStatus(memberId, StatusConstants.APPROVED)
//                .stream()
//                .flatMap(studyRepository::findById)
//                .map(StudyDTO::new)
//                .collect(Collectors.toList());
    }


    /**
     * 스터디 멤버 수 조회
     *
     * @param memberId 회원 ID
     * @return 가입한 스터디 수
     */
    public long countStudiesByMemberId(String memberId) {
        return studyMemberRepository.countByMemberId(memberId);
    }

    public Optional<StudyMember> getStudyMemberById(Long studyMemberId) {
        return studyMemberRepository.findById(studyMemberId);
    }


}
