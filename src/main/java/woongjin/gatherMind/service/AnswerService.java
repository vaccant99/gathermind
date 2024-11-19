package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import woongjin.gatherMind.DTO.AnswerDTO;
import woongjin.gatherMind.DTO.AnswerDTOInQuestion;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.exception.answer.AnswerNotFoundException;
import woongjin.gatherMind.exception.member.MemberNotFoundException;
import woongjin.gatherMind.exception.question.QuestionNotFoundException;
import woongjin.gatherMind.repository.AnswerRepository;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.repository.QuestionRepository;
import woongjin.gatherMind.DTO.AnswerCreateRequestDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;

    public Answer addAnswer(AnswerDTO answerDto) {
        // question Id를 이용해서 question 을 찾아서 저장해야합니다!
        Answer answer = new Answer();
        answer.setStudyId(answerDto.getStudyId());
        answer.setContent(answerDto.getContent());
        answer.setCreatedAt(LocalDateTime.now());
        answer.setMemberId(answerDto.getMemberId());
        return answerRepository.save(answer);
    }

    public Optional<Answer> getAnswerById(Long answerId) {
        return answerRepository.findById(answerId);
    }

    public AnswerDTO convertToDto(Answer answer) {
        AnswerDTO dto = new AnswerDTO();
        dto.setAnswerId(answer.getAnswerId());
        dto.setStudyId(answer.getStudyId());
        dto.setContent(answer.getContent());
        dto.setCreatedAt(answer.getCreatedAt());
        dto.setMemberId(answer.getMemberId());
        return dto;
    }

    public List<AnswerDTO> findRecentAnswersByMemberId(String memberId) {
        List<Answer> answers = answerRepository.findRecentAnswersByMemberId(memberId);
        return answers.stream()
                .map(answer -> new AnswerDTO(answer)) // Answer를 AnswerDTO로 변환
                .collect(Collectors.toList());
    }

    // 댓글 페이징 조회
    public Page<AnswerDTOInQuestion> getAnswersByQuestionId(Long questionId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return this.answerRepository.findAnswersByQuestionId(questionId, pageable);
    }

    // 댓글 생성
    public AnswerDTOInQuestion createAnswer(AnswerCreateRequestDTO answerDTO) {
        Member member = this.memberRepository
                .findById(answerDTO.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException("not found Member by memberId"));

        Question question = this.questionRepository
                .findById(answerDTO.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException("not found Question by questionId"));

        Answer answer = new Answer();
        answer.setContent(answerDTO.getContent());
        answer.setQuestion(question);
        answer.setMemberId(member.getMemberId());

        Answer newAnswer = this.answerRepository.save(answer);

        return AnswerDTOInQuestion.builder()
                .answerId(newAnswer.getAnswerId())
                .content(newAnswer.getContent())
                .createdAt(newAnswer.getCreatedAt())
                .memberId(newAnswer.getMemberId())
                .nickname(member.getNickname())
                .build();
    }

    // 댓글 수정
    public AnswerDTOInQuestion updateAnswer(Long answerId, String content) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("not found Answer by answerId"));

        answer.setContent(content);

        Answer updatedAnswer = this.answerRepository.save(answer);

        return AnswerDTOInQuestion.builder()
                .answerId(updatedAnswer.getAnswerId())
                .content(updatedAnswer.getContent())
                .createdAt(updatedAnswer.getCreatedAt())
                .memberId(updatedAnswer.getMemberId())
                .nickname(updatedAnswer.getMember().getNickname())
                .build();
    }

    // 댓글 삭제
    public Answer deleteAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("not found Answer by answerId"));

        this.answerRepository.delete(answer);

        return answer;
    }
}
