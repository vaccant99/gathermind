package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.ScheduleDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Schedule;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.exception.schedule.ScheduleNotFoundException;
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

    private final StudyService studyService;
    private final MemberService memberService;

    // 일정 생성
    public Schedule createSchedule(ScheduleDTO scheduleDTO, String memberId) {

        Study study = studyService.findStudyByStudyId(scheduleDTO.getStudyId());

        Member member = memberService.findByMemberId(memberId);

        Schedule schedule = toEntity(scheduleDTO, member.getMemberId());
        schedule.setStudy(study);

        return this.scheduleRepository.save(schedule);
    }

    // 일정 수정
    public Schedule updateSchedule(Long scheduleId, ScheduleDTO scheduleDTO) {

        Schedule schedule = findByScheduleId(scheduleId);

        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setDateTime(scheduleDTO.getDateTime());
        schedule.setLocation(scheduleDTO.getLocation());
        schedule.setDescription(scheduleDTO.getDescription());

        return this.scheduleRepository.save(schedule);
    }

    // 일정 삭제
    public Schedule deleteSchedule(Long scheduleId) {

        Schedule schedule = findByScheduleId(scheduleId);

        this.scheduleRepository.delete(schedule);

        return schedule;
    }

    public Optional<Schedule> getScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId);
    }

    public Schedule findByScheduleId(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() ->
                new ScheduleNotFoundException(scheduleId));
    }

    private Schedule toEntity(ScheduleDTO dto, String memberId) {
        Schedule schedule = new Schedule();
        schedule.setTitle(dto.getTitle());
        schedule.setDescription(dto.getDescription());
        schedule.setDateTime(dto.getDateTime());
        schedule.setLocation(dto.getLocation());
        schedule.setMemberId(memberId);
        return schedule;
    }
}
