package woongjin.gatherMind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.ScheduleDTO;
import woongjin.gatherMind.entity.Schedule;
import woongjin.gatherMind.service.ScheduleService;

@Controller
@RequestMapping(value = "/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // 일정 생성
    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(scheduleDTO));
    }

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
