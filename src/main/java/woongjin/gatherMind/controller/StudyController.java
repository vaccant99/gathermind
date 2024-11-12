package woongjin.gatherMind.controller;

import org.springframework.http.ResponseEntity;
import woongjin.gatherMind.service.StudyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studies")
public class StudyController {


    private StudyService studyService;

    @GetMapping("/titles")
    public ResponseEntity<List<String>> getAllStudyTitles() {
        List<String> studyTitles = studyService.getAllStudyTitles();
        return ResponseEntity.ok(studyTitles);
    }
}