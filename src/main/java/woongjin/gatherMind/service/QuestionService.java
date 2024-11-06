package woongjin.gatherMind.service;

import woongjin.gatherMind.DTO.QuestionDTO;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public Question addQuestion(QuestionDTO questionDto) {
        Question question = new Question();
        question.setMemberId(questionDto.getMemberId());
        question.setStudyId(questionDto.getStudyId());
        question.setContent(questionDto.getContent());
        question.setCreatedAt(LocalDateTime.now());
        question.setTitle(questionDto.getTitle());
        question.setOption(questionDto.getOption());
        return questionRepository.save(question);
    }

    public Optional<Question> getQuestionById(Long questionId) {
        return questionRepository.findById(questionId);
    }

    public QuestionDTO convertToDto(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setQuestionId(question.getQuestionId());
        dto.setMemberId(question.getMemberId());
        dto.setStudyId(question.getStudyId());
        dto.setContent(question.getContent());
        dto.setCreatedAt(question.getCreatedAt());
        dto.setTitle(question.getTitle());
        dto.setOption(question.getOption());
        return dto;
    }
}
