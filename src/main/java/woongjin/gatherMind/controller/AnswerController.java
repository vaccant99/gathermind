package woongjin.gatherMind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.AnswerCreateRequestDTO;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.service.AnswerService;

@Controller
@RequestMapping(value = "/api/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

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
