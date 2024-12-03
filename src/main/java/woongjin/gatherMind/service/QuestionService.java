package woongjin.gatherMind.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.entity.*;
import woongjin.gatherMind.exception.unauthorized.UnauthorizedActionException;
import woongjin.gatherMind.exception.notFound.MemberNotFoundException;
import woongjin.gatherMind.exception.notFound.QuestionNotFoundException;
import woongjin.gatherMind.exception.notFound.StudyNotFoundException;
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

    private final CommonLookupService commonLookupService;
    private final FileService fileService;

    /**
     * 질문 생성
     *
     * @param questionDTO 질문 생성 정보
     * @param memberId    생성하는 회원 ID
     * @param studyId     소속 스터디 ID
     * @return 생성된 질문 객체
     * @throws MemberNotFoundException 회원 ID가 존재하지 않을 경우
     * @throws StudyNotFoundException  스터디 ID가 존재하지 않을 경우
     */
    @Transactional
    public Question createQuestionWithFile(QuestionCreateWithFileDTO questionDTO, String memberId, Long studyId) {

        Member byMemberId = commonLookupService.findByMemberId(memberId);
        Study study = commonLookupService.findStudyByStudyId(studyId);

        StudyMember studyMember = commonLookupService.findByMemberIdAndStudyId(memberId, studyId);

        Question question = new Question();
        question.setOption(questionDTO.getOption());
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());
        question.setStudyMember(studyMember);

        Question savedQuestion = this.questionRepository.save(question);
        handleFileForQuestion(questionDTO, savedQuestion, memberId);

        return savedQuestion;
    }

    /**
     * 질문 조회 (파일 포함)
     *
     * @param questionId 조회할 질문 ID
     * @return 질문 및 파일 URL DTO
     * @throws QuestionNotFoundException 질문 ID가 존재하지 않을 경우
     */
    public QuestionWithFileUrlDTO getQuestionWithFileUrl(Long questionId) {

        Question question = findByQuestionId(questionId);
        FileMetadataUrlDTO fileMetaDTO = fileMetadataRepository.findByEntityFileMapping_Question_QuestionId(questionId);

        String fileName = "";
        String fullUrlByKey = "";
        if (fileMetaDTO != null) {
            fullUrlByKey = fileService.getFullUrlByKey(fileMetaDTO.getFileKey());
            fileName = fileMetaDTO.getFileName();
        }

        return new QuestionWithFileUrlDTO(question, fileName, fullUrlByKey);
    }

    /**
     * 질문 수정
     *
     * @param questionId  수정할 질문 ID
     * @param questionDTO 수정 정보
     * @param memberId    수정하는 회원 ID
     * @return 수정된 질문 객체
     */
    @Transactional
    public Question updateQuestionWithFile(Long questionId, QuestionCreateWithFileDTO questionDTO, String memberId) {

        Question originQuestion = checkQuestionOwnership(questionId, memberId);

//        deleteExistingFilesForQuestion(questionId);

        originQuestion.setOption(questionDTO.getOption());
        originQuestion.setTitle(questionDTO.getTitle());
        originQuestion.setContent(questionDTO.getContent());

        Question savedQuestion = this.questionRepository.save(originQuestion);

        handleFileForQuestion(questionDTO, savedQuestion, memberId);

        return savedQuestion;
    }

    /**
     * 질문 삭제
     *
     * @param questionId 삭제할 질문 ID
     * @param memberId   삭제하는 회원 ID
     * @throws UnauthorizedActionException 질문에 대한 권한이 없을 경우
     */
    @Transactional
    public void deleteQuestion(Long questionId, String memberId) {

        Question question = checkQuestionOwnership(questionId, memberId);

        deleteExistingFilesForQuestion(questionId);

        this.questionRepository.delete(question);
    }

    /**
     * 특정 회원이 작성한 최근 질문 목록 조회
     *
     * @param memberId 회원 ID
     * @return 질문 DTO 목록
     */
    public List<QuestionDTO> findRecentQuestionsByMemberId(String memberId) {
        List<Question> top3ByStudyMemberMemberMemberIdOrderByCreatedAtDesc =
                questionRepository.findTop3ByStudyMember_Member_MemberIdOrderByCreatedAtDesc(memberId);
        return top3ByStudyMemberMemberMemberIdOrderByCreatedAtDesc.stream().map(QuestionDTO::new).collect(Collectors.toList());
    }

    /**
     * 특정 회원이 작성한 질문 수 조회
     *
     * @param memberId 회원 ID
     * @return 질문 개수
     */
    public long countQuestionsByMemberId(String memberId) {
        return questionRepository.countByMemberId(memberId);
    }

    /**
     * 질문 ID로 질문 객체 조회
     *
     * @param questionId 질문 ID
     * @return 질문 객체
     * @throws QuestionNotFoundException 질문이 존재하지 않을 경우
     */
    public Question findByQuestionId(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundException(questionId));
    }

    /**
     * 질문 소유권 확인
     *
     * @param questionId 질문 ID
     * @param memberId   회원 ID
     * @return 질문 객체
     * @throws UnauthorizedActionException 질문에 대한 권한이 없을 경우
     */
    private Question checkQuestionOwnership(Long questionId, String memberId) {
        Question question = findByQuestionId(questionId);
        commonLookupService.checkMemberExists(memberId);

        if (!question.getStudyMember().getMember().getMemberId().equals(memberId)) {
            throw new UnauthorizedActionException("이 질문에 대한 권한이 없습니다.");
        }
        return question;
    }

    /**
     * 질문과 관련된 파일 처리
     *
     * @param questionDTO 질문 정보 DTO
     * @param question    질문 객체
     * @param memberId    회원 ID
     */
    private void handleFileForQuestion(QuestionCreateWithFileDTO questionDTO, Question question, String memberId) {
        Optional.ofNullable(questionDTO.getFile())
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> {
                    deleteExistingFilesForQuestion(question.getQuestionId());
                    EntityFileMapping entityFileMapping = new EntityFileMapping();
                    entityFileMapping.setQuestion(question);
                    entityFileMapping.setStudyMember(question.getStudyMember());
                    fileService.handleFileUpload(file, memberId, entityFileMapping);
                });
    }

    /**
     * 질문과 관련된 기존 파일 삭제
     *
     * @param questionId 질문 ID
     */
    private void deleteExistingFilesForQuestion(Long questionId) {
        EntityFileMapping existingMapping = entityFileMappingRepository.findByQuestion_QuestionId(questionId);

        if (existingMapping != null) {
            entityFileMappingRepository.delete(existingMapping);
            fileMetadataRepository.delete(existingMapping.getFileMetadata());
            fileService.deleteFileFromS3(existingMapping.getFileMetadata().getFileKey());
        }

    }
}
