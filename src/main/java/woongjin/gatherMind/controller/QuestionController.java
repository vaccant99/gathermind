package woongjin.gatherMind.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.AnswerDTOInQuestion;
import woongjin.gatherMind.DTO.QuestionDTO;
import woongjin.gatherMind.DTO.QuestionCreateDTO;
import woongjin.gatherMind.DTO.QuestionInfoDTO;

import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.service.AnswerService;
import woongjin.gatherMind.service.QuestionService;

@RestController
@RequestMapping(value = "api/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final JwtTokenProvider jwtTokenProvider;

    // 질문(게시글) 생성
    @PostMapping
    public ResponseEntity<Question> createQuestion(HttpServletRequest request, @RequestBody QuestionCreateDTO questionDTO, @RequestParam Long studyId) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return new ResponseEntity<>(this.questionService.createQuestion(questionDTO, memberId, studyId), HttpStatus.CREATED);
    }

    // 질문 상세 데이터 조회 (댓글 포함)
    @GetMapping(value = "/detail/{id}")
    public ResponseEntity<QuestionInfoDTO> getDetailQuestion(@PathVariable Long id) {
        return new ResponseEntity<>(this.questionService.getQuestion(id), HttpStatus.OK);
    }
    @GetMapping("/{id}/answers")
    public Page<AnswerDTOInQuestion> getAnswersByQuestion(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return answerService.getAnswersByQuestionId(id, page, size);
    }
    // 질문 수정
    @PutMapping(value = "/{id}")
    public ResponseEntity<Question> updateQuestion(HttpServletRequest request, @PathVariable Long id, @RequestBody Question question) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return new ResponseEntity<>(this.questionService.updateQuestion(id, question, memberId), HttpStatus.OK);
    }

    // 질문 삭제
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteQuestion(HttpServletRequest request, @PathVariable Long id) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return new ResponseEntity<>(this.questionService.deleteQuestion(id, memberId), HttpStatus.OK);
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
