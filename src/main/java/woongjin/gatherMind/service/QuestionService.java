package woongjin.gatherMind.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.entity.*;
import woongjin.gatherMind.exception.UnauthorizedActionException;
import woongjin.gatherMind.exception.question.QuestionNotFoundException;
import woongjin.gatherMind.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final FileMetadataRepository fileMetadataRepository;
    private final EntityFileMappingRepository entityFileMappingRepository;

    private final MemberService memberService;
    private final FileService fileService;
    private final StudyService studyService;
    private final StudyMemberService studyMemberService;

    // 질문 생성
    @Transactional
    public Question createQuestionWithFile(QuestionCreateWithFileDTO questionDTO, String memberId, Long studyId) {

        memberService.findByMemberId(memberId);

        studyService.findStudyByStudyId(studyId);

        StudyMember studyMember = studyMemberService.findByMemberIdAndStudyId(memberId, studyId);

        Question question = new Question();
        question.setOption(questionDTO.getOption());
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());
        question.setStudyMember(studyMember);

        Question savedQuestion = this.questionRepository.save(question);
        handleFileForQuestion(questionDTO, savedQuestion, memberId);

        return savedQuestion;
    }

    // 질문 조회
    public QuestionWithFileUrlDTO getQuestionWithFileUrl(Long questionId) {

        Question question = findByQuestionId(questionId);

        Member member = memberService.findByMemberId(question.getStudyMember().getMember().getMemberId());

        FileMetadataUrlDTO fileMetaDTO = fileMetadataRepository.findByEntityFileMapping_Question_QuestionId(questionId);

        String fullUrlByKey = "";
        if (!fileMetaDTO.getFileKey().isEmpty()) {
            fullUrlByKey = fileService.getFullUrlByKey(fileMetaDTO.getFileKey());
        }

        return QuestionWithFileUrlDTO.builder().questionId(question.getQuestionId()).option(question.getOption()).title(question.getTitle()).content(question.getContent()).createdAt(question.getCreatedAt()).memberId(member.getMemberId()).nickname(member.getNickname()).fileName(fileMetaDTO.getFileName()).url(fullUrlByKey).build();
    }

    // 질문 수정
    @Transactional
    public Question updateQuestionWithFile(Long questionId, QuestionCreateWithFileDTO questionDTO, String memberId) {

        Question originQuestion = findByQuestionId(questionId);

        memberService.findByMemberId(memberId);

        if (!originQuestion.getStudyMember().getMember().getMemberId().equals(memberId)) {
            throw new UnauthorizedActionException();
        }

        EntityFileMapping existingMapping = entityFileMappingRepository.findByQuestion_QuestionId(questionId);

        if (questionDTO.getFile() != null && existingMapping != null) {
            fileMetadataRepository.delete(existingMapping.getFileMetadata());
            entityFileMappingRepository.delete(existingMapping);
            fileService.deleteFileFromS3(existingMapping.getFileMetadata().getFileKey());
        }

        originQuestion.setOption(questionDTO.getOption());
        originQuestion.setTitle(questionDTO.getTitle());
        originQuestion.setContent(questionDTO.getContent());

        Question savedQuestion = this.questionRepository.save(originQuestion);

        handleFileForQuestion(questionDTO, savedQuestion, memberId);

        return this.questionRepository.save(originQuestion);
    }

    // 질문 삭제
    @Transactional
    public void deleteQuestion(Long questionId, String memberId) {

        Question question = findByQuestionId(questionId);

        memberService.findByMemberId(memberId);

        if (!question.getStudyMember().getMember().getMemberId().equals(memberId)) {
            throw new UnauthorizedActionException();
        }

        EntityFileMapping existingMapping = entityFileMappingRepository.findByQuestion_QuestionId(questionId);

        if (existingMapping != null) {
            fileMetadataRepository.delete(existingMapping.getFileMetadata());
            entityFileMappingRepository.delete(existingMapping);
            fileService.deleteFileFromS3(existingMapping.getFileMetadata().getFileKey());
        }

        this.questionRepository.delete(question);
    }

    public Optional<Question> getQuestionById(Long questionId) {
        return questionRepository.findById(questionId);
    }

    public List<QuestionDTO> findRecentQuestionsByMemberId(String memberId) {
        List<Question> questions = questionRepository.findTop3ByMember_MemberIdOrderByCreatedAtDesc(memberId);
        return questions.stream().map(QuestionDTO::new).collect(Collectors.toList());
    }

    public long countQuestionsByMemberId(String memberId) {
        return questionRepository.countByMemberId(memberId);
    }

    public Question findByQuestionId(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundException(questionId));
    }

    private void handleFileForQuestion(QuestionCreateWithFileDTO questionDTO, Question question, String memberId) {
        Optional.ofNullable(questionDTO.getFile()).filter(file -> !file.isEmpty()).ifPresent(file -> {
            EntityFileMapping entityFileMapping = new EntityFileMapping();
            entityFileMapping.setQuestion(question);
            entityFileMapping.setStudyMember(question.getStudyMember());
            fileService.handleFileUpload(file, memberId, entityFileMapping);
        });
    }
}
