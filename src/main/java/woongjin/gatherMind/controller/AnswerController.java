package woongjin.gatherMind.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import woongjin.gatherMind.DTO.AnswerCreateRequestDTO;
import woongjin.gatherMind.DTO.AnswerDTO;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.service.AnswerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

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

    // 댓글 작성
    @PostMapping
    public ResponseEntity<Answer> createAnswer(@RequestBody AnswerCreateRequestDTO answerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(answerService.createAnswer(answerDTO));
    }

    // 댓글 수정
    @PutMapping(value = "/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable Long id, @RequestBody String content) {
        return ResponseEntity.status(HttpStatus.OK).body(answerService.updateAnswer(id, content));
    }

    // 댓글 삭제
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long id) {
        return new ResponseEntity<>(this.answerService.deleteAnswer(id), HttpStatus.OK);
    }
}