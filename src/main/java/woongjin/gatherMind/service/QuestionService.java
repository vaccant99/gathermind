package woongjin.gatherMind.service;

import woongjin.gatherMind.DTO.QuestionDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.repository.MemberRepository;
import woongjin.gatherMind.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.repository.StudyMemberRepository;
import woongjin.gatherMind.repository.StudyRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyRepository studyRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, MemberRepository memberRepository,
                           StudyMemberRepository studyMemberRepository, StudyRepository studyRepository) {
        this.questionRepository = questionRepository;
        this.memberRepository = memberRepository;
        this.studyMemberRepository = studyMemberRepository;
        this.studyRepository = studyRepository;
    }

    public Question addQuestion(QuestionDTO questionDto) {
        Question question = new Question();

        Member member = memberRepository.findByMemberId(questionDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Long studyId = studyRepository.findByTitle(questionDto.getStudyTitle())
                .map(Study::getStudyId)
                .orElseThrow(() -> new RuntimeException("Study not found"));

        StudyMember studyMember = studyMemberRepository.findByMember_MemberIdAndStudy_StudyId(member.getMemberId(), studyId)
                .orElseThrow(() -> new RuntimeException("StudyMember not found"));

        question.setContent(questionDto.getContent());
        question.setCreatedAt(LocalDateTime.now());
        question.setTitle(questionDto.getTitle());
        question.setMember(member); // member 설정

        return questionRepository.save(question);
    }

    public Optional<Question> getQuestionById(Long questionId) {
        return questionRepository.findById(questionId);
    }

    public QuestionDTO convertToDto(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setQuestionId(question.getQuestionId());
        dto.setContent(question.getContent());
        dto.setCreatedAt(question.getCreatedAt());
        dto.setTitle(question.getTitle());
        return dto;
    }

    public List<QuestionDTO> findRecentQuestionsByMemberId(String memberId) {
        List<Question> questions = questionRepository.findTop3ByMember_MemberIdOrderByCreatedAtDesc(memberId);
        return questions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
