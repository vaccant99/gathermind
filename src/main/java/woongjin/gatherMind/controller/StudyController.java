package woongjin.gatherMind.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.service.StudyService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    // 스터디 생성
    @PostMapping
    public ResponseEntity<Study> createStudy(@RequestBody StudyCreateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body( studyService.createStudy(dto));
    }

    // 스터디 조회
    @GetMapping("/{studyId}")
    public ResponseEntity<StudyDTO> getStudy(@PathVariable Long studyId) {
        return ResponseEntity.status(HttpStatus.CREATED).body( studyService.getStudyById2(studyId));
    }

    // 스터디 수정
    @PutMapping("/{studyId}")
    public ResponseEntity<Study> updateStudy(@PathVariable Long studyId, @RequestBody Study study) {
        return ResponseEntity.ok(studyService.updateStudy(studyId, study));
    }

    // 스터디 정보, 멤버 조회
    @GetMapping("/{studyId}/members")
    public  ResponseEntity<StudyWithMembersDTO> getStudyById(@PathVariable Long studyId) {
        StudyWithMembersDTO dto = studyService.getStudyInfoWithMembers(studyId);
        return ResponseEntity.ok(dto);
    }

    // 스터디 멤버와 게시판 조회
    @GetMapping("/{studyId}/members/boards")
    public  ResponseEntity<MemberAndBoardDTO> getMemberByStudyId(@PathVariable Long studyId) {
        MemberAndBoardDTO dto = studyService.getMembersAndBoard(studyId);
        return ResponseEntity.ok(dto);
    }

    // 스터디 게시판 조회
    @GetMapping("/{studyId}/boards")
    public  ResponseEntity<Page<QuestionWithoutAnswerDTO>> getBoardsByStudyId(
            @PathVariable Long studyId,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<QuestionWithoutAnswerDTO> boards = studyService.getBoards(studyId, page, size);
        return ResponseEntity.ok(boards);
    }

    // 그룹 약속 조회
    @GetMapping("/{studyId}/schedules")
    public  ResponseEntity<List<ScheduleResponseDTO>> getSchedules(
            @PathVariable Long studyId
    ) {
        List<ScheduleResponseDTO> schedules = studyService.getScheduleByStudyId(studyId);
        return ResponseEntity.ok(schedules != null ? schedules : Collections.emptyList());
    }
}
