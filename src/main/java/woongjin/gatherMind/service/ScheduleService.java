package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.ScheduleDTO;
import woongjin.gatherMind.entity.Schedule;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.exception.study.StudyNotFoundException;
import woongjin.gatherMind.repository.ScheduleRepository;
import woongjin.gatherMind.repository.StudyRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final StudyRepository studyRepository;

    // 일정 생성
    public Schedule createSchedule(ScheduleDTO scheduleDTO) {

        Study study = studyRepository.findById(scheduleDTO.getStudyId()).orElseThrow(() ->
                new StudyNotFoundException("study not found by studyId"));

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

    public Schedule addSchedule(ScheduleDTO scheduleDto) {
        Schedule schedule = new Schedule();
//        schedule.setStudyId(scheduleDto.getStudyId());
        schedule.setTitle(scheduleDto.getTitle());
        schedule.setDescription(scheduleDto.getDescription());
        schedule.setDateTime(scheduleDto.getDateTime());
        schedule.setLocation(scheduleDto.getLocation());
        return scheduleRepository.save(schedule);
    }

    public Optional<Schedule> getScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId);
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
