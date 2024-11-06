package woongjin.gatherMind.controller;

import woongjin.gatherMind.DTO.AnswerDTO;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @PostMapping("/add")
    public AnswerDTO addAnswer(@RequestBody AnswerDTO answerDto) {
        Answer answer = answerService.addAnswer(answerDto);
        return answerService.convertToDto(answer);
    }

    @GetMapping("/{answerId}")
    public AnswerDTO getAnswerById(@PathVariable Long answerId) {
        Answer answer = answerService.getAnswerById(answerId).orElse(null);
        return answer != null ? answerService.convertToDto(answer) : null;
    }
}
