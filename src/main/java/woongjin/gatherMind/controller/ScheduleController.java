package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
import woongjin.gatherMind.DTO.StudyCreateRequestDTO;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.entity.Schedule;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.service.ScheduleService;

@RestController
@RequestMapping(value = "/api/schedule")
@Tag(name = "Schedule API")
@RequiredArgsConstructor
public class ScheduleController {
    
    private final ScheduleService scheduleService;
    private final JwtTokenProvider jwtTokenProvider;


    @Operation(
            summary = "스터디 일정 생성",
            description = "스터디의 일정을 생성합니다. 요청 본문에는 수정할 스터디 일정 정보가 포함된 ScheduleDTO 객체를 전달합니다."
    )
    @PostMapping
    public ResponseEntity<Schedule> createSchedule(HttpServletRequest request, @RequestBody ScheduleDTO scheduleDTO) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(scheduleDTO, memberId));
    }


    @Operation(
        summary = "스터디 일정 수정"
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<Schedule> updateQuestion(@PathVariable Long id, @RequestBody ScheduleDTO scheduleDTO) {
        return new ResponseEntity<>(this.scheduleService.updateSchedule(id, scheduleDTO), HttpStatus.OK);
    }


    @Operation(
            summary = "스터디 일정 삭제"
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        return new ResponseEntity<>(this.scheduleService.deleteSchedule(id), HttpStatus.OK);
    }


    @GetMapping("/{scheduleId}")
    public ScheduleDTO getScheduleById(@PathVariable Long scheduleId) {
        Schedule schedule = scheduleService.findByScheduleId(scheduleId);
        return schedule != null ? new ScheduleDTO(schedule) : null;
    }

}
