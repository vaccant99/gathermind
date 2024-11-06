package woongjin.gatherMind.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.MemberAndBoardDTO;
import woongjin.gatherMind.DTO.StudyWithMembersDTO;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.service.StudyService;

@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    // 스터디 생성
    @PostMapping
    public ResponseEntity<Study> createMeeting(@RequestBody Study meeting) {
        return ResponseEntity.status(HttpStatus.CREATED).body( studyService.createMeeting(meeting));
    }

    // 스터디 정보, 멤버 조회
    @GetMapping("/{id}")
    public  ResponseEntity<StudyWithMembersDTO> getMeetingById(@PathVariable Long id) {
        StudyWithMembersDTO dto = studyService.getMeetingWithMembers(id);
            return ResponseEntity.ok(dto);
    }

    // 그룹 멤버 조회
    @GetMapping("/{id}/member")
    public  ResponseEntity<MemberAndBoardDTO> getMemberByMeetingId(@PathVariable Long id) {
        MemberAndBoardDTO dto = studyService.getMembersAndBoard(id);
        return ResponseEntity.ok(dto);
    }
//
//    // 그룹 약속 조회
//    @GetMapping("/{meetingId}/appointment/{memberId}")
//    public  ResponseEntity<Page<AppointmentAndAttendCheckDTO>> getAppointmentAndAttendCheckByMeetingIdAndMemberId(
//            @PathVariable Long meetingId,
//            @PathVariable String memberId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size
//                        ) {
//
//        Page<AppointmentAndAttendCheckDTO> appointments = meetingService.getAppointmentAndAttendCheckByMeetingIdAndMemberId(meetingId, memberId,
//                page, size
//        );
//
//        return ResponseEntity.ok(appointments);
//    }
}

