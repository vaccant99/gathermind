package woongjin.gatherMind.controller;

import woongjin.gatherMind.DTO.ScheduleDTO;
import woongjin.gatherMind.entity.Schedule;
import woongjin.gatherMind.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/add")
    public ScheduleDTO addSchedule(@RequestBody ScheduleDTO scheduleDto) {
        Schedule schedule = scheduleService.addSchedule(scheduleDto);
        return scheduleService.convertToDto(schedule);
    }

    @GetMapping("/{scheduleId}")
    public ScheduleDTO getScheduleById(@PathVariable Long scheduleId) {
        Schedule schedule = scheduleService.getScheduleById(scheduleId).orElse(null);
        return schedule != null ? scheduleService.convertToDto(schedule) : null;
    }
}
