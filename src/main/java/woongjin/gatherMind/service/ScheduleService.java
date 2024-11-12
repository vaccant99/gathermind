package woongjin.gatherMind.service;

import woongjin.gatherMind.DTO.ScheduleDTO;
import woongjin.gatherMind.entity.Schedule;
import woongjin.gatherMind.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule addSchedule(ScheduleDTO scheduleDto) {
        Schedule schedule = new Schedule();
        schedule.setStudyId(scheduleDto.getStudyId());
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
        dto.setStudyId(schedule.getStudyId());
        dto.setTitle(schedule.getTitle());
        dto.setDescription(schedule.getDescription());
        dto.setDateTime(schedule.getDateTime());
        dto.setLocation(schedule.getLocation());
        return dto;
    }
}