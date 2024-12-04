package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.DTO.AnswerDTO;
import woongjin.gatherMind.DTO.AnswerDTOInQuestion;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.exception.unauthorized.UnauthorizedActionException;
import woongjin.gatherMind.exception.notFound.AnswerNotFoundException;
import woongjin.gatherMind.repository.AnswerRepository;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.AnswerCreateRequestDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Question;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final CommonLookupService commonLookupService;
    private final QuestionService questionService;

    /**
     * 답변 ID를 통해 답변을 조회합니다.
     *
     * @param answerId 답변의 ID
     * @return 답변이 존재하면 Optional로 반환하고, 없으면 비어 있는 Optional 반환
     */
    public Optional<Answer> getAnswerById(Long answerId) {
        return Optional.ofNullable(findByAnswerId(answerId));
    }

    /**
     * 특정 질문에 대한 답변 목록을 페이지 단위로 조회합니다.
     *
     * @param questionId 질문의 ID
     * @param page       조회할 페이지 번호
     * @param size       한 페이지에 표시할 답변 개수
     * @return 페이지화된 답변 목록
     */
    public Page<AnswerDTOInQuestion> getAnswersByQuestionId(Long questionId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return this.answerRepository.findAnswersByQuestionId(questionId, pageable);
    }

    /**
     * 새로운 답변을 생성합니다.
     *
     * @param answerDTO 답변 생성에 필요한 데이터를 담은 DTO
     * @param memberId  답변을 생성하는 사용자의 ID
     * @return 생성된 답변을 DTO로 반환
     */
    @Transactional
    public AnswerDTOInQuestion createAnswer(AnswerCreateRequestDTO answerDTO, String memberId) {

        Member member = commonLookupService.findByMemberId(memberId);

        Question question = questionService.findByQuestionId(answerDTO.getQuestionId());

        Answer answer = new Answer();
        answer.setContent(answerDTO.getContent());
        answer.setQuestion(question);
        answer.setMemberId(member.getMemberId());
        answer.setMember(member);
        answer.setStudyId(question.getStudyMember().getStudy().getStudyId());

        Answer newAnswer = this.answerRepository.save(answer);

        return new AnswerDTOInQuestion(newAnswer);
    }

    /**
     * 기존 답변의 내용을 수정합니다.
     *
     * @param answerId 수정할 답변의 ID
     * @param content  수정된 내용
     * @param memberId 답변을 수정하려는 사용자의 ID
     * @return 수정된 답변을 DTO로 반환
     * @throws UnauthorizedActionException 사용자가 수정 권한이 없을 경우 예외 발생
     */
    @Transactional
    public AnswerDTOInQuestion updateAnswer(Long answerId, String content, String memberId) {
        Answer answer = findByAnswerId(answerId);

        commonLookupService.findByMemberId(memberId);

        if (!answer.getMemberId().equals(memberId)) {
            throw new UnauthorizedActionException();
        }

        answer.setContent(content);
        Answer updatedAnswer = this.answerRepository.save(answer);

        return new AnswerDTOInQuestion(updatedAnswer);
    }

    /**
     * 답변을 삭제합니다.
     *
     * @param answerId 삭제할 답변의 ID
     * @param memberId 답변을 삭제하려는 사용자의 ID
     * @throws UnauthorizedActionException 사용자가 삭제 권한이 없을 경우 예외 발생
     */
    @Transactional
    public void deleteAnswer(Long answerId, String memberId) {
        Answer answer = findByAnswerId(answerId);

        commonLookupService.findByMemberId(memberId);

        if (!answer.getMemberId().equals(memberId)) {
            throw new UnauthorizedActionException();
        }

        this.answerRepository.delete(answer);
    }

    /**
     * 특정 사용자가 작성한 최신 답변 목록을 조회합니다.
     *
     * @param memberId 사용자의 ID
     * @return 최신 답변 목록을 DTO로 반환
     */
    public List<AnswerDTO> findRecentAnswersByMemberId(String memberId) {
        List<Answer> answers = answerRepository.findRecentAnswersByMemberId(memberId);
        return answers.stream()
                .map(answer -> new AnswerDTO(answer)) // Answer를 AnswerDTO로 변환
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자가 작성한 모든 답변 목록을 조회합니다.
     *
     * @param memberId 사용자의 ID
     * @return 답변 목록을 DTO로 반환
     */
    public List<AnswerDTO> findAnswersByMemberId(String memberId) {
        List<Answer> answers = answerRepository.findByMemberId(memberId);
        return answers.stream()
                .map(AnswerDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자가 작성한 답변 개수를 반환합니다.
     *
     * @param memberId 사용자의 ID
     * @return 사용자가 작성한 답변 개수
     */
    public long countAnswersByMemberId(String memberId) {
        return answerRepository.countByMemberId(memberId);
    }

    /**
     * 답변 ID를 통해 답변을 조회합니다.
     *
     * @param answerId 답변의 ID
     * @return 답변 엔티티
     * @throws AnswerNotFoundException 답변을 찾을 수 없을 경우 예외 발생
     */
    public Answer findByAnswerId(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException(answerId));
    }
}
