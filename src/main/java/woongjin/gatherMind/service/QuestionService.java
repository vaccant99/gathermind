package woongjin.gatherMind.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.entity.*;
import woongjin.gatherMind.exception.unauthorized.UnauthorizedActionException;
import woongjin.gatherMind.exception.notFound.MemberNotFoundException;
import woongjin.gatherMind.exception.notFound.QuestionNotFoundException;
import woongjin.gatherMind.exception.notFound.StudyNotFoundException;
import woongjin.gatherMind.repository.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final FileMetadataRepository fileMetadataRepository;
    private final EntityFileMappingRepository entityFileMappingRepository;

    private final CommonLookupService commonLookupService;
    private final FileService fileService;

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

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

        commonLookupService.findByMemberId(memberId);
        commonLookupService.findStudyByStudyId(studyId);

        StudyMember studyMember = commonLookupService.findByMemberIdAndStudyId(memberId, studyId);

        Question question = new Question();
        question.setOption(questionDTO.getOption());
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());
        question.setStudyMember(studyMember);

        Question savedQuestion = this.questionRepository.save(question);

        // 파일(폼을 통해 입력) 매핑
        handleFileForQuestion(questionDTO, savedQuestion, memberId);

        // 이미지 파일(에디터를 통해 입력) 매핑
        List<String> fileKeys = extractFileKeysFromContent(questionDTO.getContent());
        fileKeys.forEach(fileKey -> linkFileToQuestion(fileKey, savedQuestion));

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
        Optional<FileMetadataUrlDTO> fileMetaDTO = fileMetadataRepository.findNonImageFilesByQuestionId(questionId);
//        FileMetadataUrlDTO fileMetaDTO = fileMetadataRepository.findByEntityFileMapping_Question_QuestionId(questionId);

        String fileName = "";
        String fullUrlByKey = "";

        if (fileMetaDTO.isPresent()) {
            FileMetadataUrlDTO metadata = fileMetaDTO.get();
            fullUrlByKey = fileService.getFullUrlByKey(metadata.getFileKey());
            fileName = metadata.getFileName();
        }

//        if (fileMetaDTO != null) {
//            fullUrlByKey = fileService.getFullUrlByKey(fileMetaDTO.getFileKey());
//            fileName = fileMetaDTO.getFileName();
//        }

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

        List<String> updatedFileKeys = extractFileKeysFromContent(questionDTO.getContent());
        deleteFilesNotInUpdatedKeys(questionId, updatedFileKeys);

        // 파일(폼을 통해 입력) 매핑
        handleFileForQuestion(questionDTO, savedQuestion, memberId);

        // 이미지 파일(에디터를 통해 입력) 매핑
        updatedFileKeys.forEach(fileKey -> linkFileToQuestion(fileKey, savedQuestion));

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

//        deleteExistingFilesForQuestion(questionId);

        deleteAllFilesForQuestion(questionId);

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
                    EntityFileMapping entityFileMapping = new EntityFileMapping();
                    entityFileMapping.setQuestion(question);
                    entityFileMapping.setStudyMember(question.getStudyMember());
                    FileUploadResponseDTO responseDTO = fileService.handleFileUpload(file, memberId);

                    FileMetadata metadata = fileMetadataRepository.findByFileKey(responseDTO.getFileKey())
                            .orElseThrow(() -> new RuntimeException("Metadata not found for fileKey: " + responseDTO.getFileKey()));
                    entityFileMapping.setFileMetadata(metadata);
                    entityFileMappingRepository.save(entityFileMapping);
                });
    }


//    private void handleFileForQuestion(QuestionCreateWithFileDTO questionDTO, Question question, String memberId) {
//        Optional.ofNullable(questionDTO.getFile())
//                .filter(file -> !file.isEmpty())
//                .ifPresent(file -> {
//                    deleteExistingFilesForQuestion(question.getQuestionId());
//                    EntityFileMapping entityFileMapping = new EntityFileMapping();
//                    entityFileMapping.setQuestion(question);
//                    entityFileMapping.setStudyMember(question.getStudyMember());
//                    fileService.handleFileUpload(file, memberId, entityFileMapping);
//                });
//    }

//    /**
//     * 질문과 관련된 기존 파일 삭제
//     *
//     * @param questionId 질문 ID
//     */
//    private void deleteExistingFilesForQuestion(Long questionId) {
//        EntityFileMapping existingMapping = entityFileMappingRepository.findByQuestion_QuestionId(questionId);
//
//        if (existingMapping != null) {
//            entityFileMappingRepository.delete(existingMapping);
//            fileMetadataRepository.delete(existingMapping.getFileMetadata());
//            fileService.deleteFileFromS3(existingMapping.getFileMetadata().getFileKey());
//        }
//
//    }

    /**
     * 기존 질문과 관련된 파일 중 업데이트된 본문에 포함되지 않은 파일을 삭제
     *
     * @param questionId 질문 ID
     * @param newFileKeys 업데이트된 게시글 본문에 포함된 이미지 파일 키 목록
     */
    private void deleteFilesNotInUpdatedKeys(Long questionId, List<String> newFileKeys) {
        List<EntityFileMapping> existingMappings = entityFileMappingRepository.findByQuestion_QuestionId(questionId);

        existingMappings.stream()
                .filter(mapping -> !newFileKeys.contains(mapping.getFileMetadata().getFileKey()))
                .forEach(mapping -> deleteFileMapping(mapping));
    }

    /**
     * 질문과 관련된 파일 모두 삭제
     *
     * @param questionId 질문 ID
     */
    private void deleteAllFilesForQuestion(Long questionId) {
        List<EntityFileMapping> existingMappings = entityFileMappingRepository.findByQuestion_QuestionId(questionId);
        existingMappings.forEach(this::deleteFileMapping);
    }

    /**
     * 특정 파일 매핑과 관련된 S3 파일 및 매핑 데이터, 메타 데이터를 삭제
     *
     * @param mapping 삭제할 파일 매핑 데이터
     */
    private void deleteFileMapping(EntityFileMapping mapping) {
        fileService.deleteFileFromS3(mapping.getFileMetadata().getFileKey());
        entityFileMappingRepository.delete(mapping);
        fileMetadataRepository.delete(mapping.getFileMetadata());
    }
    /**
     * 게시글 내용에서 이미지 URL의 S3 키를 추출
     *
     * @param content 게시글 내용
     * @return 추출된 S3 파일 키 목록
     */
    private List<String> extractFileKeysFromContent(String content) {
        List<String> fileKeys = new ArrayList<>();
        String regex = "src=['\"]https?://.+?/(.+?)['\"]"; // S3 URL에서 파일 키 추출
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            try {
                String encodedKey = matcher.group(1);
                String decodedKey = URLDecoder.decode(encodedKey, StandardCharsets.UTF_8.name());
                fileKeys.add(decodedKey);
            } catch (Exception e) {
                logger.error("URL 디코딩 실패: " + matcher.group(1), e);
            }
        }
        return fileKeys;
    }

    /**
     * 주어진 S3 파일 키와 질문을 매핑
     *
     * @param fileKey 이미지의 S3 파일 키
     * @param question 매핑할 질문 객체
     */
    @Transactional
    public void linkFileToQuestion(String fileKey, Question question) {
        // 파일 메타데이터 조회
        FileMetadata metadata = fileMetadataRepository.findByFileKey(fileKey)
                .orElseThrow(() -> new RuntimeException("Metadata not found for fileKey: " + fileKey));

        // 이미 매핑된 경우 중복 생성 방지
        EntityFileMapping alreadyFileMapping = entityFileMappingRepository.findByFileMetadata_FileMetadataId(metadata.getFileMetadataId());
        if (alreadyFileMapping != null) {
            return; // 중복 방지
        }

        // 파일 매핑 정보 저장
        EntityFileMapping fileMapping = new EntityFileMapping();
        fileMapping.setFileMetadata(metadata);
        fileMapping.setQuestion(question);
        fileMapping.setStudyMember(question.getStudyMember());
        entityFileMappingRepository.save(fileMapping);
        }
}
