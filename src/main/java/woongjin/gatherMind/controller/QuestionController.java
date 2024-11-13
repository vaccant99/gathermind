package woongjin.gatherMind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.QuestionDTO;
import woongjin.gatherMind.DTO.QuestionCreateDTO;
import woongjin.gatherMind.DTO.QuestionInfoDTO;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.service.QuestionService;

@Controller
@RequestMapping(value = "/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // 질문(게시글) 생성
    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody QuestionCreateDTO questionDTO, @RequestParam String memberId, @RequestParam Long studyId) {
        return new ResponseEntity<>(this.questionService.createQuestion(questionDTO, memberId, studyId), HttpStatus.CREATED);
    }

    // 질문 상세 데이터 조회
    @GetMapping(value = "/{id}")
    public ResponseEntity<QuestionInfoDTO> getDetailQuestion(@PathVariable Long id) {
        return new ResponseEntity<>(this.questionService.getQuestion(id), HttpStatus.OK);
    }

    // 질문 수정
    @PutMapping(value = "/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        return new ResponseEntity<>(this.questionService.updateQuestion(id, question), HttpStatus.OK);
    }

    // 질문 삭제
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        return new ResponseEntity<>(this.questionService.deleteQuestion(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public QuestionDTO addQuestion(@RequestBody QuestionDTO questionDto) {
        Question question = questionService.addQuestion(questionDto);
        return questionService.convertToDto(question);
    }

    @GetMapping("/{questionId}")
    public QuestionDTO getQuestionById(@PathVariable Long questionId) {
        Question question = questionService.getQuestionById(questionId).orElse(null);
        return question != null ? questionService.convertToDto(question) : null;
    }

}
