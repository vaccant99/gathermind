package woongjin.gatherMind.controller;

import woongjin.gatherMind.DTO.StudyMemberDTO;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.service.StudyMemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/study-members")
public class StudyMemberController {

    private StudyMemberService studyMemberService;

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
}