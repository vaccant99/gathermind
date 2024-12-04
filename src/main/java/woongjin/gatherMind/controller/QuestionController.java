package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.*;

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
    public ResponseEntity<Question> createQuestionWithFile(HttpServletRequest request,
                                                           @Valid @ModelAttribute QuestionCreateWithFileDTO questionDTO,
                                                           @RequestParam Long studyId) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return new ResponseEntity<>(this.questionService.createQuestionWithFile(questionDTO, memberId, studyId), HttpStatus.CREATED);
    }

    @Operation(
            summary = "질문 상세"
    )
    @GetMapping(value = "/detail/{id}")
    public ResponseEntity<QuestionWithFileUrlDTO> getDetailQuestionWithFileUrl(@PathVariable Long id) {
        return new ResponseEntity<>(this.questionService.getQuestionWithFileUrl(id), HttpStatus.OK);
    }


    @Operation(
            summary = "질문 수정"
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<Question> updateQuestionWithFile(HttpServletRequest request,
                                                           @PathVariable Long id,
                                                           @Valid @ModelAttribute QuestionCreateWithFileDTO questionDTO) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return new ResponseEntity<>(this.questionService.updateQuestionWithFile(id, questionDTO, memberId), HttpStatus.OK);
    }

    @Operation(
            summary = "질문 삭제"
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<QuestionInfoDTO> deleteQuestion(HttpServletRequest request, @PathVariable Long id) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        this.questionService.deleteQuestion(id, memberId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/{questionId}")
    public QuestionDTO getQuestionById(@PathVariable Long questionId) {
        Question question = questionService.findByQuestionId(questionId);
        return question != null ? new QuestionDTO(question) : null;
    }

    @GetMapping("/{id}/answers")
    public Page<AnswerDTOInQuestion> getAnswersByQuestion(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return answerService.getAnswersByQuestionId(id, page, size);
    }

}
