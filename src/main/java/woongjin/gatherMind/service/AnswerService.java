package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.AnswerCreateRequestDTO;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.repository.AnswerRepository;
import woongjin.gatherMind.repository.MemberRepository;
import woongjin.gatherMind.repository.QuestionRepository;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;

    // 댓글 생성
    public Answer createAnswer(AnswerCreateRequestDTO answerDTO) {
        Member member = this.memberRepository
                .findById(answerDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("not found Member by memberId"));

        Question question = this.questionRepository
                .findById(answerDTO.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("not found Question by questionId"));

        Answer answer = new Answer();
        answer.setContent(answerDTO.getContent());
        answer.setQuestion(question);
        answer.setMemberId(member.getMemberId());

        return this.answerRepository.save(answer);
    }

    // 댓글 수정
    public Answer updateAnswer(Long answerId, String content) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("not found Answer by answerId"));

        answer.setContent(content);

        return this.answerRepository.save(answer);
    }

    // 댓글 삭제
    public Answer deleteAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("not found Answer by answerId"));

        this.answerRepository.delete(answer);

        return answer;
    }
}
