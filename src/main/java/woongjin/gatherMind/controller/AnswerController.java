package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import woongjin.gatherMind.DTO.AnswerCreateRequestDTO;
import woongjin.gatherMind.DTO.AnswerDTO;
import woongjin.gatherMind.DTO.AnswerDTOInQuestion;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.service.AnswerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answer")
@RequiredArgsConstructor
@Tag(name = "Answer API")
public class AnswerController {

    private final AnswerService answerService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{answerId}")
    public AnswerDTO getAnswerById(@PathVariable Long answerId) {
        Answer answer = answerService.getAnswerById(answerId).orElse(null);
        return answer != null ? new AnswerDTO(answer) : null;
    }

    // 댓글 작성
    @Operation(
            summary = "댓글 생성"
    )
    @PostMapping
    public ResponseEntity<AnswerDTOInQuestion> createAnswer(HttpServletRequest request, @RequestBody AnswerCreateRequestDTO answerDTO) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(answerService.createAnswer(answerDTO, memberId));
    }

    // 댓글 수정
    @Operation(
            summary = "댓글 수정"
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<AnswerDTOInQuestion> updateAnswer(HttpServletRequest request, @PathVariable Long id, @RequestBody String content) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return ResponseEntity.status(HttpStatus.OK).body(answerService.updateAnswer(id, content, memberId));
    }

    // 댓글 삭제
    @Operation(
            summary = "댓글 삭제"
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AnswerDTOInQuestion> deleteAnswer(HttpServletRequest request, @PathVariable Long id) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        this.answerService.deleteAnswer(id, memberId);
        return ResponseEntity.noContent().build();
    }// 204 No Content}
}