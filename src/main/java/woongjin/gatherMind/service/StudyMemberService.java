package woongjin.gatherMind.service;


import lombok.RequiredArgsConstructor;
import woongjin.gatherMind.DTO.StudyDTO;
import woongjin.gatherMind.DTO.StudyMemberDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.repository.StudyMemberRepository;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.repository.StudyRepository;
import woongjin.gatherMind.util.StudyMemberStatus;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyMemberService {

    private final StudyMemberRepository studyMemberRepository;

    private final StudyRepository studyRepository;

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

    // memberId로 가입한 스터디 목록을 가져오기
    public List<StudyDTO> findStudiesByMemberId(String memberId) {
        // memberId로 StudyMember 목록 조회
        List<StudyMember> studyMembers = studyMemberRepository.findByMember_MemberId(memberId);

        // StudyMember 목록을 StudyDTO로 매핑하여 반환합니다.
        return studyMembers.stream()
                .map(studyMember -> new StudyDTO(studyMember.getStudy().getTitle(), studyMember.getStudy().getDescription()))
                .collect(Collectors.toList());
    }


    public List<StudyDTO> getStudiesbyMemberId(String memberId) {


        List<Long> studyIds = studyMemberRepository.findStudyIdsByMemberId(memberId);

        if (studyIds.isEmpty()) {

            throw new NoSuchElementException("No studies found for the member with ID " + memberId);
        }

        List<Study> studies = studyRepository.findAllByStudyIdIn(studyIds);

        return studies.stream()
                .map(study -> new StudyDTO(
                        study.getStudyId(),
                        study.getTitle(),
                        study.getDescription(),
                        study.getStatus(),
                        study.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }



    public StudyMemberDTO convertToDto(StudyMember studyMember) {
        StudyMemberDTO dto = new StudyMemberDTO();
        dto.setStudyMemberId(studyMember.getStudyMemberId());
        dto.setStudyMemberId(studyMember.getStudyMemberId());
        dto.setStudyId(studyMember.getStudy().getStudyId());
        dto.setRole(studyMember.getRole());
        dto.setStatus(studyMember.getStatus());
        dto.setProgress(studyMember.getProgress());
        return dto;
    }
    // 1, 그룹 참가요청
    public StudyMemberDTO joinStudy(String memberId, Long studyId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new IllegalArgumentException("Study not found"));

        Member member = new Member(); // 예시: memberId로 Member를 조회해야 함
        member.setMemberId(memberId);

        StudyMember studyMember = new StudyMember();
        studyMember.setMember(member);
        studyMember.setStudy(study);
        studyMember.setStatus("PENDING");
        studyMember.setRole("MEMBER");
        studyMember.setProgress("NOT_STARTED");

        StudyMember savedStudyMember = studyMemberRepository.save(studyMember);

        return convertToDto(savedStudyMember); // DTO로 변환하여 반환
    }

    // 2. 멤버 승인
    public StudyMemberDTO approveMember(Long studyMemberId) {
        StudyMember studyMember = studyMemberRepository.findById(studyMemberId)
                .orElseThrow(() -> new IllegalArgumentException("Study member not found"));

        if ("PENDING".equals(studyMember.getStatus())) {
            studyMember.setStatus("APPROVED");
            StudyMember updatedStudyMember = studyMemberRepository.save(studyMember);
            return convertToDto(updatedStudyMember); // DTO로 변환하여 반환
        } else {
            throw new IllegalStateException("This member has already been approved or is in another state.");
        }
    }



     //내스터디 status approve만 조회
    public List<StudyDTO> getStudiesByMemberIdandStatus(String memberId) {
        String approvedStatus = "APPROVED";


        System.out.println("Member ID: " + memberId + ", Status: " + approvedStatus);

        List<Long> studyIds = studyMemberRepository.findStudyIdsByMemberIdAndStatus(memberId,approvedStatus);

        if (studyIds.isEmpty()) {
            throw new NoSuchElementException("No approved studies found for the member with ID " + memberId);
        }

        List<Study> studies = studyRepository.findAllByStudyIdIn(studyIds);

        return studies.stream()
                .map(study -> new StudyDTO(
                        study.getStudyId(),
                        study.getTitle(),
                        study.getDescription(),
                        study.getStatus(),
                        study.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

}
