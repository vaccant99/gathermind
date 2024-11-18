package woongjin.gatherMind.service;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import woongjin.gatherMind.DTO.StudyDTO;
import woongjin.gatherMind.DTO.StudyMemberDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.exception.member.MemberNotFoundException;
import woongjin.gatherMind.exception.study.StudyNotFoundException;
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
    private static final String STATUS_PENDING = "승인대기";

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
    public StudyMember applyStudy(HttpServletRequest request, Long studyId) {

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

        return studyMemberRepository.save(studyMember);
    }

    // memberId로 가입한 스터디 목록을 가져오기
    public List<StudyDTO> findStudiesByMemberId(String memberId) {
        // memberId로 StudyMember 목록 조회
        List<StudyMember> studyMembers = studyMemberRepository.findByMember_MemberId(memberId);

        // StudyMember 목록을 StudyDTO로 매핑하여 반환합니다.
        return studyMembers.stream()
                .map(studyMember -> new StudyDTO(studyMember.getStudy().getTitle(), studyMember.getStudy().getDescription()))
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
}
