package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.ScheduleDTO;
import woongjin.gatherMind.entity.Schedule;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.exception.study.StudyNotFoundException;
import woongjin.gatherMind.repository.ScheduleRepository;
import woongjin.gatherMind.repository.StudyRepository;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final StudyRepository studyRepository;

    // 일정 생성
    public Schedule createSchedule(ScheduleDTO scheduleDTO) {

        Study study = studyRepository.findById(scheduleDTO.getStudyId()).orElseThrow(() -> new StudyNotFoundException("study not found by studyId"));

        Schedule schedule = toEntity(scheduleDTO);
        schedule.setStudy(study);

        return this.scheduleRepository.save(schedule);
    }

    private Schedule toEntity(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        schedule.setTitle(dto.getTitle());
        schedule.setDescription(dto.getDescription());
        schedule.setDateTime(dto.getDateTime());
        schedule.setLocation(dto.getLocation());
        return schedule;
    }
}
