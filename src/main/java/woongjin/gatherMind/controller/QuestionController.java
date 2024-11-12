package woongjin.gatherMind.controller;

import woongjin.gatherMind.DTO.QuestionDTO;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.service.QuestionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private QuestionService questionService;

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