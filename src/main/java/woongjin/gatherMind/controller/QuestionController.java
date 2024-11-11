package woongjin.gatherMind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.QuestionDTO;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.service.QuestionService;

@Controller
@RequestMapping(value = "api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // 질문(게시글) 생성
    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody QuestionDTO questionDTO, @RequestParam String memberId, @RequestParam Long studyId) {
        return new ResponseEntity<>(this.questionService.createQuestion(questionDTO, memberId, studyId), HttpStatus.CREATED);
    }

    // 질문 상세 데이터 조회
    @GetMapping(value = "/{id}")
    public ResponseEntity<QuestionDTO> getDetailQuestion(@PathVariable Long id) {
        return new ResponseEntity<>(this.questionService.getQuestion(id), HttpStatus.OK);
    }
}
