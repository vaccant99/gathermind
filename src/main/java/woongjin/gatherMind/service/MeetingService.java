package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.entity.Meeting;
import woongjin.gatherMind.exception.meeting.MeetingNotFoundException;
import woongjin.gatherMind.mapper.MemberMapper;
import woongjin.gatherMind.repository.AppointmentRepository;
import woongjin.gatherMind.repository.MeetingRepository;
import woongjin.gatherMind.repository.MemberRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MemberRepository memberRepository;
    private final MeetingRepository meetingRepository;
    private final AppointmentRepository appointmentRepository;

    // 그룹 생성
    public Meeting createMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    // 그룹 정보, 멤버 랭킹, 멤버 조회
    public MeetingWithMembersDTO getMeetingWithMembers(Long meetingId) {

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingNotFoundException("그룹이 없습니다."));
        List<MemberLateCountDTO> memberByMeetingIdOrderByLate = convertToDto(memberRepository.findMemberByMeetingIdOrderByLate(meetingId));

        List<MemberDTO> members = MemberMapper.convertToMemberDtoList(memberRepository.findMemberByMeetingId(meetingId));

        return new MeetingWithMembersDTO(meeting.getMeetingId(), meeting.getMeetingName(),  meeting.getMeetingInfo(), memberByMeetingIdOrderByLate, members);
    }

    // 멤버 랭킹, 멤버 조회
    public MemberAndRankDTO getMembersAndRank(Long meetingId) {

        List<MemberLateCountDTO> memberByMeetingIdOrderByLate = convertToDto(memberRepository.findMemberByMeetingIdOrderByLate(meetingId));
        List<MemberDTO> members = MemberMapper.convertToMemberDtoList(memberRepository.findMemberByMeetingId(meetingId));

        return new MemberAndRankDTO(memberByMeetingIdOrderByLate,members);
    }

    // 그룹 약속 조회
    public List<AppointmentDTO> getAppointmentByMeetingId(Long meetingId) {
        return appointmentRepository.findAppointmentByMeetingId(meetingId).stream()
                .map(appointment -> AppointmentDTO.builder()
                        .appointmentId(appointment.getAppointmentId())
                        .appointmentName(appointment.getAppointmentName())
                        .appointmentTime(appointment.getAppointmentTime())
                        .location(appointment.getLocation())
                        .appointmentStatus(appointment.getAppointmentStatus())
                        .appointmentCreatedId(appointment.getAppointmentCreatedId())
                        .penalty(appointment.getPenalty())
                        .build())
                .collect(Collectors.toList());
    }

    // 그룹 약속 조회 + 참석여부 체크
    public Page<AppointmentAndAttendCheckDTO> getAppointmentAndAttendCheckByMeetingIdAndMemberId(
            Long meetingId, String memberId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> appointmentsPage = appointmentRepository.findAppointmentAndAttendCheckByMeetingIdAndMemberId(meetingId, memberId, pageable);

        List<AppointmentAndAttendCheckDTO> appointmentAndAttendCheckDTOS = convertToAppointAndAttendCheckDto(appointmentsPage.getContent());
        return new PageImpl<>(appointmentAndAttendCheckDTOS, pageable,appointmentsPage.getTotalElements());
    }

    // MemberLateCountDTO 변환 메서드
    private List<MemberLateCountDTO> convertToDto(List<Object[]> results) {

        return results.stream()
                .map(result -> MemberLateCountDTO.builder()
                        .memberId((String) result[0])
                        .nickname((String) result[1])
                        .appointmentId(((Number) result[2]).longValue())
                        .lateCount(((Number) result[3]).intValue())
                        .lateTime(((Number) result[4]).intValue())
                        .build())
                .collect(Collectors.toList());
    }

    // AppointmentAndAttendCheckDTO 변환 메서드
    private List<AppointmentAndAttendCheckDTO> convertToAppointAndAttendCheckDto(List<Object[]> results) {

        return results.stream()
                .map(result -> AppointmentAndAttendCheckDTO.builder()
                        .appointmentId(((Number) result[0]).longValue())
                        .appointmentCreatedId((String) result[1])
                        .appointmentName((String) result[2])
                        .appointmentStatus((Boolean) result[3])
                        .appointmentTime(((Timestamp) result[4]).toLocalDateTime())
                        .createdAt(((Timestamp) result[5]).toLocalDateTime())
                        .location((String) result[6])
                        .penalty((String) result[7])
                        .isAttend((Boolean) result[8])
                        .build())
                .collect(Collectors.toList());
    }

}
