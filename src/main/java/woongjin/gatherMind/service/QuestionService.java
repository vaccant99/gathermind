package woongjin.gatherMind.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import woongjin.gatherMind.DTO.AnswerDTO;
import woongjin.gatherMind.DTO.QuestionDTO;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.repository.AnswerRepository;
import woongjin.gatherMind.repository.QuestionRepository;
import woongjin.gatherMind.repository.StudyMemberRepository;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.repository.MemberRepository;
import woongjin.gatherMind.repository.StudyRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;

    // 질문(게시글) 생성
    @PostMapping
    public Question createQuestion(QuestionDTO questionDTO, String memberId, Long studyId) {

        StudyMember studyMember = this.studyMemberRepository
                .findByMemberIdAndStudyId(memberId, studyId)
                .orElseThrow(() -> new IllegalArgumentException("not found studyMember by memberId and studyId"));

        Question question = toEntity(questionDTO);
        question.setStudyMember(studyMember);

        return this.questionRepository.save(question);
    }

    // 질문 상세 데이터 조회
    public QuestionDTO getQuestion(Long questionId) {
        Question question = this.questionRepository
                .findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("not found question by id"));

        List<AnswerDTO> answers = this.answerRepository.findAnswersByQuestionId(questionId);

        return QuestionDTO.builder()
                .questionId(question.getQuestionId())
                .option(question.getOption())
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .answers(answers)
                .build();
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

        // studyTitle 설정
        if (question.getStudy() != null) { // Study가 null이 아닌 경우에만 설정
            dto.setStudyTitle(question.getStudy().getTitle());
        }

        return dto;
    }

    public List<QuestionDTO> findRecentQuestionsByMemberId(String memberId) {
        List<Question> questions = questionRepository.findTop3ByMember_MemberIdOrderByCreatedAtDesc(memberId);
        return questions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private Question toEntity(QuestionDTO dto) {
        Question question = new Question();
        question.setOption(dto.getOption());
        question.setTitle(dto.getTitle());
        question.setContent(dto.getContent());
        return question;
    }

    public List<QuestionDTO> findQuestionsByMemberId(String memberId) {
        List<Question> questions = questionRepository.findByMemberId(memberId);
        return questions.stream()
                .map(QuestionDTO::new)
                .collect(Collectors.toList());
    }

    public long countQuestionsByMemberId(String memberId) {
        return questionRepository.countByMemberId(memberId);
    }

}
