package woongjin.gatherMind.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.entity.Meeting;
import woongjin.gatherMind.service.MeetingService;

@RestController
@RequestMapping("/api/meeting")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    // 미팅 생성
    @PostMapping
    public ResponseEntity<Meeting> createMeeting(@RequestBody Meeting meeting) {
        return ResponseEntity.status(HttpStatus.CREATED).body( meetingService.createMeeting(meeting));
    }

    // 미팅 정보 조회 (member와 랭킹, 그룹 정보 포함)
    @GetMapping("/{id}")
    public  ResponseEntity<MeetingWithMembersDTO> getMeetingById(@PathVariable Long id) {
            MeetingWithMembersDTO dto = meetingService.getMeetingWithMembers(id);
            return ResponseEntity.ok(dto);
    }

    // 그룹 멤버 조회
    @GetMapping("/{id}/member")
    public  ResponseEntity<MemberAndRankDTO> getMemberByMeetingId(@PathVariable Long id) {
        MemberAndRankDTO dto = meetingService.getMembersAndRank(id);
        return ResponseEntity.ok(dto);
    }

    // 그룹 약속 조회
    @GetMapping("/{meetingId}/appointment/{memberId}")
    public  ResponseEntity<Page<AppointmentAndAttendCheckDTO>> getAppointmentAndAttendCheckByMeetingIdAndMemberId(
            @PathVariable Long meetingId,
            @PathVariable String memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
                        ) {

        Page<AppointmentAndAttendCheckDTO> appointments = meetingService.getAppointmentAndAttendCheckByMeetingIdAndMemberId(meetingId, memberId,
                page, size
        );

        return ResponseEntity.ok(appointments);
    }
}

