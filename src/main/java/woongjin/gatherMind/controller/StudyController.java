package woongjin.gatherMind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.dto.StudyDto;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.service.StudyService;

import java.util.List;

@RestController
@RequestMapping("/study")
public class StudyController {

    @Autowired
    private StudyService studyService;

    @GetMapping("/{studyId}")
    public ResponseEntity<StudyDto> getStudy(@PathVariable Long studyId) {
        StudyDto studyDto = studyService.getStudy(studyId);
        return ResponseEntity.ok(studyDto);
    }

    @GetMapping
    public ResponseEntity<List<StudyDto>> getAllStudies() {

        List<StudyDto> studyDTOs = studyService.getAllStudies();
        return ResponseEntity.ok(studyDTOs);
    }

    @PostMapping("/register")
    public ResponseEntity<Study> registerStudy(@RequestBody StudyDto studyDto) {
        Study createdStudy = studyService.createStudy(studyDto);
        return new ResponseEntity<>(createdStudy, HttpStatus.CREATED);}

        @GetMapping("/findbymember/{memberId}")
        public List<StudyDto> getStudiesbyStudyId(@PathVariable String memberId) {
            return studyService.getStudiesbyMemberId(memberId);
        }
}
