package woongjin.gatherMind.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import woongjin.gatherMind.dto.StudyDto;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.service.StudyMemberService;
import woongjin.gatherMind.service.StudyService;

import java.util.List;

@RestController("/studymember")
public class StudyMemberController {

     @Autowired
    private StudyMemberService studyMemberService;

    @Autowired
    private StudyService studyService;



}
