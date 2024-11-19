package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import woongjin.gatherMind.DTO.AnswerDTO;
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

    public Optional<Answer> getAnswerById(Long answerId) {
        return answerRepository.findById(answerId);
    }

    // 댓글 생성
    public Answer createAnswer(AnswerCreateRequestDTO answerDTO) {
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

        return this.answerRepository.save(answer);
    }

    // 댓글 수정
    public Answer updateAnswer(Long answerId, String content) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("not found Answer by answerId"));

        answer.setContent(content);

        return this.answerRepository.save(answer);
    }

    // 댓글 삭제
    public Answer deleteAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("not found Answer by answerId"));

        this.answerRepository.delete(answer);

        return answer;
    }

    public List<AnswerDTO> findRecentAnswersByMemberId(String memberId) {
        List<Answer> answers = answerRepository.findRecentAnswersByMemberId(memberId);
        return answers.stream()
                .map(answer -> new AnswerDTO(answer)) // Answer를 AnswerDTO로 변환
                .collect(Collectors.toList());
    }

    public List<AnswerDTO> findAnswersByMemberId(String memberId) {
        List<Answer> answers = answerRepository.findByMemberId(memberId);
        return answers.stream()
                .map(AnswerDTO::new)
                .collect(Collectors.toList());
    }

    public long countAnswersByMemberId(String memberId) {
        return answerRepository.countByMemberId(memberId);
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

}
