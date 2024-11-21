package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Question API")
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final JwtTokenProvider jwtTokenProvider;


    @Operation(
            summary = "질문(게시글) 생성"
    )
    @PostMapping
    public ResponseEntity<Question> createQuestion(HttpServletRequest request, @RequestBody QuestionCreateDTO questionDTO, @RequestParam Long studyId) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return new ResponseEntity<>(this.questionService.createQuestion(questionDTO, memberId, studyId), HttpStatus.CREATED);
    }


    @Operation(
            summary = "질문 상세, 댓글 조회"
    )
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

    @Operation(
            summary = "질문 수정"
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        return new ResponseEntity<>(this.questionService.updateQuestion(id, question), HttpStatus.OK);
    }


    @Operation(
            summary = "질문 삭제"
    )
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
