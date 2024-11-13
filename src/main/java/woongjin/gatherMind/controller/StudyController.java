package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Study API")
public class StudyController {

    private final StudyService studyService;

    // 스터디 생성
    @PostMapping
    @Operation(summary = "스터디 생성", description = "스터디 이름, 설명, 상태, 생성자 ID가 포함된 객체가 필요합니다.")
    public ResponseEntity<Study> createStudy(@RequestBody StudyCreateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body( studyService.createStudy(dto));
    }

    // 스터디 조회
    @GetMapping("/{studyId}")
    @Operation(
            summary = "스터디 조회",
            description = "스터디 ID를 경로 변수로 받아 해당 스터디의 상세 정보를 조회합니다."
    )
    public ResponseEntity<StudyInfoDTO> getStudy(@PathVariable Long studyId) {
        return ResponseEntity.status(HttpStatus.CREATED).body( studyService.getStudyByStudyId(studyId));
    }

    // 스터디 수정
    @PutMapping("/{studyId}")
    @Operation(
            summary = "스터디 수정",
            description = "스터디 ID를 경로 변수로 받아 해당 스터디의 정보를 수정합니다. 요청 본문에는 수정할 스터디 정보가 포함된 Study 객체를 전달합니다."
    )
    public ResponseEntity<StudyInfoDTO> updateStudy(@PathVariable Long studyId, @RequestBody Study study) {
        return ResponseEntity.ok(studyService.updateStudy(studyId, study));
    }

    // 스터디 정보, 멤버 조회
    @GetMapping("/{studyId}/members")
    @Operation(
            summary = "스터디 정보 및 멤버 조회",
            description = "스터디 ID를 경로 변수로 받아 해당 스터디의 상세 정보와 멤버 정보를 조회합니다."
    )
    public  ResponseEntity<StudyWithMembersDTO> getStudyById(@PathVariable Long studyId) {
        StudyWithMembersDTO dto = studyService.getStudyInfoWithMembers(studyId);
        return ResponseEntity.ok(dto);
    }

    // 스터디 멤버와 게시판 조회
    @GetMapping("/{studyId}/members/boards")
    @Operation(
            summary = "스터디 멤버 및 게시판 조회",
            description = "스터디 ID를 경로 변수로 받아 해당 스터디의 멤버와 게시판 정보를 조회합니다."
    )
    public  ResponseEntity<MemberAndBoardDTO> getMemberByStudyId(@PathVariable Long studyId) {
        MemberAndBoardDTO dto = studyService.getMembersAndBoard(studyId);
        return ResponseEntity.ok(dto);
    }

    // 스터디 게시판 조회
    @GetMapping("/{studyId}/boards")
    @Operation(
            summary = "스터디 게시판 조회",
            description = "스터디 ID를 경로 변수로 받아 해당 스터디의 게시판 목록을 페이지 형태로 조회합니다. 페이지와 크기를 쿼리 파라미터로 설정할 수 있습니다."
    )
    public  ResponseEntity<Page<QuestionWithoutAnswerDTO>> getBoardsByStudyId(
            @PathVariable Long studyId,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<QuestionWithoutAnswerDTO> boards = studyService.getBoards(studyId, page, size);
        return ResponseEntity.ok(boards);
    }

    // 그룹 약속 조회
    @GetMapping("/{studyId}/schedules")
    @Operation(
            summary = "스터디 일정 조회",
            description = "스터디 ID를 경로 변수로 받아 해당 스터디의 일정 목록을 조회합니다."
    )
    public  ResponseEntity<List<ScheduleResponseDTO>> getSchedules(
            @PathVariable Long studyId
    ) {
        List<ScheduleResponseDTO> schedules = studyService.getScheduleByStudyId(studyId);
        return ResponseEntity.ok(schedules != null ? schedules : Collections.emptyList());
    }
}
