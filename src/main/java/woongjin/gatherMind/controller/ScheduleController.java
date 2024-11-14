package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.ScheduleDTO;
import woongjin.gatherMind.entity.Schedule;
import woongjin.gatherMind.service.ScheduleService;

@RestController
@RequestMapping(value = "/api/schedule")
@Tag(name = "Schedule API")
@RequiredArgsConstructor
public class ScheduleController {
    
    private final ScheduleService scheduleService;

    // 일정 생성
    @Operation(
            summary = "스터디 일정 생성",
            description = "스터디의 일정을 생성합니다. 요청 본문에는 수정할 스터디 일정 정보가 포함된 ScheduleDTO 객체를 전달합니다."
    )
    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(scheduleDTO));
    }

//    @PostMapping
//    public ScheduleDTO addSchedule(@RequestBody ScheduleDTO scheduleDto) {
//        Schedule schedule = scheduleService.addSchedule(scheduleDto);
//        return scheduleService.convertToDto(schedule);
//    }

    @GetMapping("/{scheduleId}")
    public ScheduleDTO getScheduleById(@PathVariable Long scheduleId) {
        Schedule schedule = scheduleService.getScheduleById(scheduleId).orElse(null);
        return schedule != null ? scheduleService.convertToDto(schedule) : null;
    }

}
