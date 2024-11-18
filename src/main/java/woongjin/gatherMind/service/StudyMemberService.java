package woongjin.gatherMind.service;


import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import woongjin.gatherMind.DTO.StudyDTO;
import woongjin.gatherMind.DTO.StudyMemberConfirmDTO;
import woongjin.gatherMind.DTO.StudyMemberDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.exception.member.MemberNotFoundException;
import woongjin.gatherMind.exception.study.StudyNotFoundException;
import woongjin.gatherMind.exception.studyMember.StudyMemberNotFoundException;
import woongjin.gatherMind.repository.MemberRepository;
import woongjin.gatherMind.repository.StudyMemberRepository;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.repository.StudyRepository;
import woongjin.gatherMind.util.JwtUtil;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyMemberService {

    private final StudyMemberRepository studyMemberRepository;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final JwtUtil jwtUtil;

    private static final String ROLE_MEMBER = "member";
    private static final String ROLE_ADMIN = "admin";
    private static final String STATUS_PENDING = "승인대기";
    private static final String STATUS_CONFIRM = "승인";

    public StudyMember addMember(StudyMemberDTO studyMemberDto) {
        StudyMember studyMember = new StudyMember();
        studyMember.setStudyMemberId(studyMemberDto.getStudyMemberId());
        studyMember.setStudyMemberId(studyMemberDto.getStudyId());
        studyMember.setRole(studyMemberDto.getRole());
        studyMember.setStatus(studyMemberDto.getStatus());
        studyMember.setProgress(studyMemberDto.getProgress());
        return studyMemberRepository.save(studyMember);
    }

    public Optional<StudyMember> getStudyMemberById(Long studyMemberId) {
        return studyMemberRepository.findById(studyMemberId);
    }

    // 스터디 지원하기
    public StudyMemberDTO applyStudy(HttpServletRequest request, Long studyId) {

        String memberId = jwtUtil.extractMemberIdFromToken(request);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberNotFoundException("Member with ID " + memberId + " not found"));

        Study study = studyRepository.findById(studyId).orElseThrow(() -> new StudyNotFoundException("study not found"));

        StudyMember studyMember = new StudyMember();
        studyMember.setRole(ROLE_MEMBER);
        studyMember.setStatus(STATUS_PENDING);
        studyMember.setProgress("");
        studyMember.setMember(member);
        studyMember.setStudy(study);

        return convertToDto(studyMemberRepository.save(studyMember));
    }

    // 스터디 지원 승인하기
    public StudyMemberDTO confirmStudyMember(HttpServletRequest request, StudyMemberConfirmDTO dto) throws UnavailableException {

        String adminId = jwtUtil.extractMemberIdFromToken(request);
        String memberId = dto.getMemberId();
        Long studyId = dto.getStudyId();

        // 관리자가 해당 스터디의 관리자 권한이 있는지 확인
        StudyMember adminMember = studyMemberRepository.findByMember_MemberIdAndStudy_StudyId(adminId, studyId)
                .orElseThrow(() -> new StudyMemberNotFoundException("Admin StudyMember not found for Member ID " + adminId + " and Study ID " + studyId));

        checkAdminRole(adminMember);  // 관리 권한 체크 메서드 호출

        // 승인할 멤버와 스터디, StudyMember 객체 가져오기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member with ID " + memberId + " not found"));

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new StudyNotFoundException("Study with ID " + studyId + " not found"));

        StudyMember studyMember = studyMemberRepository.findByMember_MemberIdAndStudy_StudyId(memberId, studyId)
                .orElseThrow(() -> new StudyMemberNotFoundException("StudyMember not found for Member ID " + memberId + " and Study ID " + studyId));

        // 스터디 지원 상태를 승인으로 변경
        studyMember.setStatus(STATUS_CONFIRM);

        // StudyMember 엔티티를 DTO로 변환하여 반환
        return convertToDto(studyMemberRepository.save(studyMember));
    }

    // memberId로 가입한 스터디 목록을 가져오기
    public List<StudyDTO> findStudiesByMemberId(String memberId) {
        // memberId로 StudyMember 목록 조회
        List<StudyMember> studyMembers = studyMemberRepository.findByMember_MemberId(memberId);

        // StudyMember 목록을 StudyDTO로 매핑하여 반환합니다.
        return studyMembers.stream()
                .map(studyMember -> StudyDTO.builder()
                        .title(studyMember.getStudy().getTitle())
                        .description(studyMember.getStudy().getDescription())
                        .studyId(studyMember.getStudy().getStudyId())
                        .build()
                )
                .collect(Collectors.toList());
    }


    public StudyMemberDTO convertToDto(StudyMember studyMember) {
        StudyMemberDTO dto = new StudyMemberDTO();
        dto.setStudyMemberId(studyMember.getStudyMemberId());
        dto.setStudyId(studyMember.getStudy().getStudyId());
        dto.setRole(studyMember.getRole());
        dto.setStatus(studyMember.getStatus());
        dto.setProgress(studyMember.getProgress());
        return dto;
    }

    private void checkAdminRole(StudyMember adminMember) throws UnavailableException {
        if (!adminMember.getRole().equals(ROLE_ADMIN)) {
            throw new UnavailableException("접근 제한됨 - 관리자가 아닙니다.");
        }
    }
}
