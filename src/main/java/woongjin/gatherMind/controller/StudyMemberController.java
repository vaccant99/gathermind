package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.UnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import woongjin.gatherMind.DTO.StudyApplyDTO;
import woongjin.gatherMind.DTO.StudyMemberConfirmDTO;
import org.springframework.http.HttpStatus;
import woongjin.gatherMind.DTO.StudyMemberDTO;
import woongjin.gatherMind.auth.CurrentMemberId;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.service.StudyMemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/study-members")
@RequiredArgsConstructor
@Tag(name = "Study Member API")
public class StudyMemberController {

    private final StudyMemberService studyMemberService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{studyMemberId}")
    public StudyMemberDTO getStudyMemberById(@PathVariable Long studyMemberId) {
        StudyMember studyMember = studyMemberService.getStudyMemberById(studyMemberId).orElse(null);
        return studyMember != null ? new StudyMemberDTO(studyMember) : null;
    }


    @Operation(
            summary = "스터디 가입 신청"
    )
    @PostMapping
    public ResponseEntity<StudyMemberDTO> applyStudy(@CurrentMemberId String memberId, @RequestBody StudyApplyDTO dto) {
        return ResponseEntity.ok(studyMemberService.applyStudy(memberId, dto.getStudyId())) ;
    }

    @Operation(
            summary = "스터디 가입 신청 승인"
    )
    @PutMapping
    public ResponseEntity<StudyMemberDTO> confirmStudyMember(@CurrentMemberId String memberId,
                                                             @RequestBody StudyMemberConfirmDTO dto)
            throws UnavailableException {

        return ResponseEntity.ok(studyMemberService.confirmStudyMember(memberId, dto));
    }

//    // 스터디 가입신청 api
//    @PostMapping("/join/{memberId}/{studyId}")
//    public ResponseEntity<StudyMemberDTO> joinStudy(@PathVariable String memberId, @PathVariable Long studyId) {
//        try {
//            StudyMemberDTO studyMemberDTO = studyMemberService.joinStudy(memberId, studyId);
//            return new ResponseEntity<>(studyMemberDTO, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // 스터디가 없을 경우
//        }
//    }

//    // 2. 관리자가 멤버를 승인하는 API
//    @PatchMapping("/approve/{studyMemberId}")
//    public ResponseEntity<StudyMemberDTO> approveMember(@PathVariable Long studyMemberId) {
//        try {
//            StudyMemberDTO approvedMemberDTO = studyMemberService.approveMember(studyMemberId);
//            return new ResponseEntity<>(approvedMemberDTO, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // 해당 멤버가 없을 경우
//        } catch (IllegalStateException e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 이미 승인된 상태일 경우
//        }
//    }
}
