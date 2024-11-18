package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import woongjin.gatherMind.DTO.AnswerDTO;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.repository.QuestionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

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

    public List<AnswerDTO> findAnswersByMemberId(String memberId) {
        List<Answer> answers = answerRepository.findByMemberId(memberId);
        return answers.stream()
                .map(AnswerDTO::new)
                .collect(Collectors.toList());
    }

    public long countAnswersByMemberId(String memberId) {
        return answerRepository.countByMemberId(memberId);
    }

}