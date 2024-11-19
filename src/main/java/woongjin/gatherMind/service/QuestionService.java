package woongjin.gatherMind.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.exception.member.MemberNotFoundException;
import woongjin.gatherMind.exception.question.QuestionNotFoundException;
import woongjin.gatherMind.exception.study.StudyNotFoundException;
import woongjin.gatherMind.exception.studyMember.StudyMemberNotFoundException;
import woongjin.gatherMind.repository.AnswerRepository;
import woongjin.gatherMind.repository.QuestionRepository;
import woongjin.gatherMind.repository.StudyMemberRepository;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.repository.MemberRepository;
import woongjin.gatherMind.repository.StudyRepository;

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
    public Question createQuestion(QuestionInfoDTO questionDTO, String memberId, Long studyId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));

        Study studyNotFound = studyRepository.findById(studyId)
                .orElseThrow(() -> new StudyNotFoundException("Study not found"));

        StudyMember studyMember = this.studyMemberRepository
                .findByMember_MemberIdAndStudy_StudyId(memberId, studyId)
                .orElseThrow(() -> new StudyMemberNotFoundException("not found studyMember by memberId and studyId"));

        Question question = toEntity(questionDTO);
        question.setStudyMember(studyMember);

        return this.questionRepository.save(question);
    }


//    public Question addQuestion(QuestionDTO questionDto) {
//        Question question = new Question();
//
//        Member member = memberRepository.findByMemberId(questionDto.getMemberId())
//                .orElseThrow(() -> new MemberNotFoundException("Member not found"));
//
//        Long studyId = studyRepository.findByTitle(questionDto.getStudyTitle())
//                .map(Study::getStudyId)
//                .orElseThrow(() -> new StudyNotFoundException("Study not found"));
//
//
//        question.setContent(questionDto.getContent());
//        question.setCreatedAt(LocalDateTime.now());
//        question.setTitle(questionDto.getTitle());
//        question.setMember(member); // member 설정
//
//        return questionRepository.save(question);
//    }

    // 질문 상세 데이터 조회
    public QuestionInfoDTO getQuestion(Long questionId) {
        Question question = this.questionRepository
                .findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("not found question by id"));

        List<AnswerDTOInQuestion> answers = this.answerRepository.findAnswersByQuestionId(questionId);

        return QuestionInfoDTO.builder()
                .questionId(question.getQuestionId())
                .option(question.getOption())
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .answers(answers)
                .build();
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

    // 질문 수정
    public Question updateQuestion(Long questionId, Question question) {
        Question originQuestion = this.questionRepository
                .findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("not found question by id"));

        originQuestion.setOption(question.getOption());
        originQuestion.setTitle(question.getTitle());
        originQuestion.setContent(question.getContent());

        return this.questionRepository.save(originQuestion);
    }

    // 질문 삭제
    public Question deleteQuestion(Long questionId) {
        Question question = this.questionRepository
                .findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("not found question by id"));

        this.questionRepository.delete(question);

        return question;
    }

    private Question toEntity(QuestionInfoDTO dto) {
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
