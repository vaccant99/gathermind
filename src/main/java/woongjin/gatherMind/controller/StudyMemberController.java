package woongjin.gatherMind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import woongjin.gatherMind.DTO.StudyDTO;
import woongjin.gatherMind.DTO.StudyMemberDTO;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.service.StudyMemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/study-members")
public class StudyMemberController {


    @Autowired
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



// 스터디 가입신청 api
    @PostMapping("/join/{memberId}/{studyId}")
    public ResponseEntity<StudyMemberDTO> joinStudy(@PathVariable String memberId, @PathVariable Long studyId) {
        try {
            StudyMemberDTO studyMemberDTO = studyMemberService.joinStudy(memberId, studyId);
            return new ResponseEntity<>(studyMemberDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // 스터디가 없을 경우
        }
    }

    // 2. 관리자가 멤버를 승인하는 API
    @PatchMapping("/approve/{studyMemberId}")
    public ResponseEntity<StudyMemberDTO> approveMember(@PathVariable Long studyMemberId) {
        try {
            StudyMemberDTO approvedMemberDTO = studyMemberService.approveMember(studyMemberId);
            return new ResponseEntity<>(approvedMemberDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // 해당 멤버가 없을 경우
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 이미 승인된 상태일 경우
        }
    }
}


