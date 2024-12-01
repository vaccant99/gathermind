package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.ScheduleDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Schedule;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.exception.member.MemberNotFoundException;
import woongjin.gatherMind.exception.schedule.ScheduleNotFoundException;
import woongjin.gatherMind.exception.study.StudyNotFoundException;
import woongjin.gatherMind.repository.MemberRepository;
import woongjin.gatherMind.repository.ScheduleRepository;
import woongjin.gatherMind.repository.StudyRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;

    // 일정 생성
    public Schedule createSchedule(ScheduleDTO scheduleDTO, String memberId) {

        Study study = studyRepository.findById(scheduleDTO.getStudyId()).orElseThrow(() ->
                new StudyNotFoundException("study not found by studyId"));

        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new MemberNotFoundException("member not found by memberId"));

        Schedule schedule = toEntity(scheduleDTO, member.getMemberId());
        schedule.setStudy(study);

        return this.scheduleRepository.save(schedule);
    }

    // 일정 수정
    public Schedule updateSchedule(Long scheduleId, ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new ScheduleNotFoundException("schedule not found by scheduleId"));

        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setDateTime(scheduleDTO.getDateTime());
        schedule.setLocation(scheduleDTO.getLocation());
        schedule.setDescription(scheduleDTO.getDescription());

        return this.scheduleRepository.save(schedule);
    }

    // 일정 삭제
    public Schedule deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new ScheduleNotFoundException("schedule not found by scheduleId"));

        this.scheduleRepository.delete(schedule);

        return schedule;
    }

    public Optional<Schedule> getScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId);
    }

//    public Schedule addSchedule(ScheduleDTO scheduleDto) {
//        Schedule schedule = new Schedule();
////        schedule.setStudyId(scheduleDto.getStudyId());
//        schedule.setTitle(scheduleDto.getTitle());
//        schedule.setDescription(scheduleDto.getDescription());
//        schedule.setDateTime(scheduleDto.getDateTime());
//        schedule.setLocation(scheduleDto.getLocation());
//        return scheduleRepository.save(schedule);
//    }

    private Schedule toEntity(ScheduleDTO dto, String memberId) {
        Schedule schedule = new Schedule();
        schedule.setTitle(dto.getTitle());
        schedule.setDescription(dto.getDescription());
        schedule.setDateTime(dto.getDateTime());
        schedule.setLocation(dto.getLocation());
        schedule.setMemberId(memberId);
        return schedule;
    }

    public ScheduleDTO convertToDto(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setScheduleId(schedule.getScheduleId());
//        dto.setStudyId(schedule.getStudyId());
        dto.setTitle(schedule.getTitle());
        dto.setDescription(schedule.getDescription());
        dto.setDateTime(schedule.getDateTime());
        dto.setLocation(schedule.getLocation());
        return dto;
    }
}
