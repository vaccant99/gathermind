package woongjin.gatherMind.service;

import woongjin.gatherMind.DTO.StudyDTO;
import woongjin.gatherMind.DTO.StudyMemberDTO;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.repository.StudyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudyMemberService {
    @Autowired
    private StudyMemberRepository studyMemberRepository;

    public StudyMember addMember(StudyMemberDTO studyMemberDto) {
        StudyMember studyMember = new StudyMember();
        studyMember.setStudyMemberId(studyMemberDto.getStudyMemberId());
        studyMember.setStudyId(studyMemberDto.getStudyId());
        studyMember.setRole(studyMemberDto.getRole());
        studyMember.setJoinDate(LocalDateTime.now());
        studyMember.setStatus(studyMemberDto.getStatus());
        studyMember.setProgress(studyMemberDto.getProgress());
        return studyMemberRepository.save(studyMember);
    }

    public Optional<StudyMember> getStudyMemberById(Long studyMemberId) {
        return studyMemberRepository.findById(studyMemberId);
    }

    // memberId로 가입한 스터디 목록을 가져오기
    public List<StudyDTO> findStudiesByMemberId(String memberId) {
        List<StudyMember> studyMembers = studyMemberRepository.findByMemberId(memberId);
        return studyMembers.stream()
                .map(studyMember -> new StudyDTO(studyMember.getStudy().getTitle(), studyMember.getStudy().getDescription()))
                .collect(Collectors.toList());
    }

    public StudyMemberDTO convertToDto(StudyMember studyMember) {
        StudyMemberDTO dto = new StudyMemberDTO();
        dto.setStudyMemberId(studyMember.getStudyMemberId());
        dto.setStudyMemberId(studyMember.getStudyMemberId());
        dto.setStudyId(studyMember.getStudyId());
        dto.setRole(studyMember.getRole());
        dto.setJoinDate(studyMember.getJoinDate());
        dto.setStatus(studyMember.getStatus());
        dto.setProgress(studyMember.getProgress());
        return dto;
    }
}
