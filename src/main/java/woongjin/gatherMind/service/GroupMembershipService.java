package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.AttendAppointmentDTO;
import woongjin.gatherMind.DTO.MemberDTO;
import woongjin.gatherMind.entity.GroupMembership;
import woongjin.gatherMind.exception.member.MemberNotFoundException;
import woongjin.gatherMind.mapper.MemberMapper;
import woongjin.gatherMind.repository.GroupMembershipRepository;
import woongjin.gatherMind.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class GroupMembershipService {

    private final GroupMembershipRepository groupMembershipRepository;
    private final MemberRepository memberRepository;


    public MemberDTO createMemberInMeeting(GroupMembership groupMembership) {

        GroupMembership saved = groupMembershipRepository.save(groupMembership);

        return memberRepository.findById(saved.getMember().getMemberId())
                .map(MemberMapper::convertToMemberDto)
                .orElseThrow(() -> new MemberNotFoundException("member Id not found : " + saved.getMember().getMemberId()));
    }

    public AttendAppointmentDTO attendAppointment(GroupMembership groupMembership) {


        GroupMembership save = groupMembershipRepository.save(groupMembership);


        return AttendAppointmentDTO.builder()
                .membershipId(save.getMembershipId())
                .appointmentId(save.getAppointment().getAppointmentId())
                .meetingId(save.getMeeting().getMeetingId())
                .memberId(save.getMember().getMemberId())
                .build();
    }

}
