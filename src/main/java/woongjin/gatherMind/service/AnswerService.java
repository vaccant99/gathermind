package woongjin.gatherMind.service;

import woongjin.gatherMind.DTO.AnswerDTO;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    public Answer addAnswer(AnswerDTO answerDto) {
        Answer answer = new Answer();
        answer.setQuestionId(answerDto.getQuestionId());
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
        dto.setQuestionId(answer.getQuestionId());
        dto.setStudyId(answer.getStudyId());
        dto.setContent(answer.getContent());
        dto.setCreatedAt(answer.getCreatedAt());
        dto.setMemberId(answer.getMemberId());
        return dto;
    }

    public List<AnswerDTO> findRecentAnswersByMemberId(String memberId) {
        List<Answer> answers = answerRepository.findRecentAnswersByMemberId(memberId);
        return answers.stream()
                .map(answer -> new AnswerDTO(answer)) // Answer 엔티티를 AnswerDTO로 변환
                .collect(Collectors.toList());
    }
}
