package woongjin.gatherMind.controller;

import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import woongjin.gatherMind.DTO.StudyApplyDTO;
import woongjin.gatherMind.DTO.StudyMemberConfirmDTO;
import woongjin.gatherMind.DTO.StudyMemberDTO;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.service.StudyMemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/study-members")
@RequiredArgsConstructor
public class StudyMemberController {

    private final StudyMemberService studyMemberService;

    @PostMapping("/add")
    public StudyMemberDTO addMember(@RequestBody StudyMemberDTO studyMemberDto) {
        StudyMember studyMember = studyMemberService.addMember(studyMemberDto);
        return studyMemberService.convertToDto(studyMember);
    }

    @GetMapping("/{studyMemberId}")
    public StudyMemberDTO getStudyMemberById(@PathVariable Long studyMemberId) {
        StudyMember studyMember = studyMemberService.getStudyMemberById(studyMemberId).orElse(null);
        return studyMember != null ? studyMemberService.convertToDto(studyMember) : null;
    }


    // 스터디 신청
    @PostMapping
    public ResponseEntity<StudyMemberDTO> applyStudy(HttpServletRequest request, @RequestBody StudyApplyDTO dto) {
        return ResponseEntity.ok(studyMemberService.applyStudy(request, dto.getStudyId())) ;
    }

    // 멤버 승인
    @PutMapping
    public ResponseEntity<StudyMemberDTO> confirmStudyMember(HttpServletRequest request, @RequestBody StudyMemberConfirmDTO dto) throws UnavailableException {
        return ResponseEntity.ok(studyMemberService.confirmStudyMember(request, dto));
    }
}