package woongjin.gatherMind.controller;

import woongjin.gatherMind.DTO.StudyDTO;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/studies")
public class StudyController {
    @Autowired
    private StudyService studyService;

    @PostMapping("/create")
    public StudyDTO createStudy(@RequestBody StudyDTO studyDto) {
        Study study = studyService.createStudy(studyDto);
        return studyService.convertToDTO(study);
    }

    @GetMapping("/{studyId}")
    public StudyDTO getStudyById(@PathVariable Long studyId) {
        Study study = studyService.getStudyById(studyId).orElse(null);
        return study != null ? studyService.convertToDTO(study) : null;
    }

    @PutMapping("/{studyId}")
    public StudyDTO updateStudy(@PathVariable Long studyId, @RequestBody StudyDTO studyDto) {
        Study study = studyService.updateStudy(studyId, studyDto);
        return studyService.convertToDTO(study);
    }
}
