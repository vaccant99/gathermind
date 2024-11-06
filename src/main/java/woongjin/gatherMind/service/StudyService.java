package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.exception.member.StudyNotFoundException;
import woongjin.gatherMind.mapper.MemberMapper;
import woongjin.gatherMind.repository.MemberRepository;
import woongjin.gatherMind.repository.QuestionRepository;
import woongjin.gatherMind.repository.StudyRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final JdbcTemplate jdbcTemplate;

    // 스터디 생성
    public Study createMeeting(Study meeting) {
        return studyRepository.save(meeting);
    }

    // 그룹 정보, 멤버 조회, 게시판 조회
    public StudyWithMembersDTO getMeetingWithMembers(Long studyId) {

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new StudyNotFoundException("스터디를 찾을 수 없습니다."));

        String sql = """
            SELECT m.member_id AS memberId, m.nickname AS nickname, sm.role AS role, sm.status AS status
            FROM member m
            JOIN study_member sm ON m.member_id = sm.member_id
            WHERE sm.study_id = ?
        """;

        List<MemberAndStatusRoleDTO> memberByStudyId = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(MemberAndStatusRoleDTO.class),
                studyId
        );

        List<QuestionDTO> byStudyId = questionRepository.findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(studyId);

        return new StudyWithMembersDTO(study.getStudyId(), study.getTitle(),  study.getDescription(), memberByStudyId, byStudyId);
    }

    // 멤버 랭킹, 멤버 조회
    public MemberAndBoardDTO getMembersAndBoard(Long studyId) {

        String sql = """
            SELECT m.member_id AS memberId, m.nickname AS nickname, sm.role AS role, sm.status AS status
            FROM member m
            JOIN study_member sm ON m.member_id = sm.member_id
            WHERE sm.study_id = ?
        """;

        List<MemberAndStatusRoleDTO> memberByStudyId = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(MemberAndStatusRoleDTO.class),
                studyId
        );

        List<QuestionDTO> byStudyId = questionRepository.findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(studyId);

        return new MemberAndBoardDTO(memberByStudyId,byStudyId);
    }
//
//    // 그룹 약속 조회
//    public List<AppointmentDTO> getAppointmentByMeetingId(Long meetingId) {
//        return appointmentRepository.findAppointmentByMeetingId(meetingId).stream()
//                .map(appointment -> AppointmentDTO.builder()
//                        .appointmentId(appointment.getAppointmentId())
//                        .appointmentName(appointment.getAppointmentName())
//                        .appointmentTime(appointment.getAppointmentTime())
//                        .location(appointment.getLocation())
//                        .appointmentStatus(appointment.getAppointmentStatus())
//                        .appointmentCreatedId(appointment.getAppointmentCreatedId())
//                        .penalty(appointment.getPenalty())
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    // 그룹 약속 조회 + 참석여부 체크
//    public Page<AppointmentAndAttendCheckDTO> getAppointmentAndAttendCheckByMeetingIdAndMemberId(
//            Long meetingId, String memberId, int page, int size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Object[]> appointmentsPage = appointmentRepository.findAppointmentAndAttendCheckByMeetingIdAndMemberId(meetingId, memberId, pageable);
//
//        List<AppointmentAndAttendCheckDTO> appointmentAndAttendCheckDTOS = convertToAppointAndAttendCheckDto(appointmentsPage.getContent());
//        return new PageImpl<>(appointmentAndAttendCheckDTOS, pageable,appointmentsPage.getTotalElements());
//    }
//
//    // MemberLateCountDTO 변환 메서드
//    private List<MemberLateCountDTO> convertToDto(List<Object[]> results) {
//
//        return results.stream()
//                .map(result -> MemberLateCountDTO.builder()
//                        .memberId((String) result[0])
//                        .nickname((String) result[1])
//                        .appointmentId(((Number) result[2]).longValue())
//                        .lateCount(((Number) result[3]).intValue())
//                        .lateTime(((Number) result[4]).intValue())
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    // AppointmentAndAttendCheckDTO 변환 메서드
//    private List<AppointmentAndAttendCheckDTO> convertToAppointAndAttendCheckDto(List<Object[]> results) {
//
//        return results.stream()
//                .map(result -> AppointmentAndAttendCheckDTO.builder()
//                        .appointmentId(((Number) result[0]).longValue())
//                        .appointmentCreatedId((String) result[1])
//                        .appointmentName((String) result[2])
//                        .appointmentStatus((Boolean) result[3])
//                        .appointmentTime(((Timestamp) result[4]).toLocalDateTime())
//                        .createdAt(((Timestamp) result[5]).toLocalDateTime())
//                        .location((String) result[6])
//                        .penalty((String) result[7])
//                        .isAttend((Boolean) result[8])
//                        .build())
//                .collect(Collectors.toList());
//    }

}
