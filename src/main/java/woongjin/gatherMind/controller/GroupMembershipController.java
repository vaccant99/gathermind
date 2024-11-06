package woongjin.gatherMind.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woongjin.gatherMind.DTO.AttendAppointmentDTO;
import woongjin.gatherMind.DTO.MemberDTO;
import woongjin.gatherMind.entity.GroupMembership;
import woongjin.gatherMind.service.GroupMembershipService;

@RestController
@RequestMapping("/api/membership")
@RequiredArgsConstructor
public class GroupMembershipController {

    private final GroupMembershipService groupMembershipService;

    // 그룹내 멤버 추가
    @PostMapping("/member")
    public ResponseEntity<MemberDTO> createMemberInMeeting(@RequestBody GroupMembership groupMembership) {

            MemberDTO memberInMeeting = groupMembershipService.createMemberInMeeting(groupMembership);
            return  ResponseEntity.ok(memberInMeeting);
    }

    // 약속 참석
    @PostMapping("/appointment")
    public ResponseEntity<AttendAppointmentDTO> attendAppointment(@RequestBody GroupMembership groupMembership) {
        return  ResponseEntity.ok(groupMembershipService.attendAppointment(groupMembership));
    }
}
