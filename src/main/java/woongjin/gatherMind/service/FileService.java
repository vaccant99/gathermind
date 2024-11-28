package woongjin.gatherMind.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import woongjin.gatherMind.DTO.FileUploadResponseDTO;
import woongjin.gatherMind.entity.EntityFileMapping;
import woongjin.gatherMind.entity.FileMetadata;
import woongjin.gatherMind.exception.FileSizeExceededException;
import woongjin.gatherMind.repository.EntityFileMappingRepository;
import woongjin.gatherMind.repository.FileMetadataRepository;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import static woongjin.gatherMind.util.UrlShortener.generateShortUrlKey;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Value("${aws.s3.base-url}")
    private String s3BaseUrl;

    @Value("${aws.s3.bucket:YOUR_BUCKET}")
    private String bucketName;


    private final S3Client s3Client;
    private final FileMetadataRepository fileMetadataRepository;
    private final EntityFileMappingRepository entityFileMappingRepository;

    @PostConstruct
    @Profile("prod") // 프로덕션 환경에서만 실행
    public void validateBucketName() {
        if ("YOUR_BUCKET".equals(bucketName)) {
            throw new IllegalStateException("AWS S3 버킷 이름이 설정되지 않았습니다.");
        }
    }

    public FileUploadResponseDTO handleFileUpload(MultipartFile file, String memberId, EntityFileMapping entityFileMapping) {
        File tempFile = null;
        try {
            // 파일 유효성 검사
            validateFile(file);

            // 원본 파일 이름 가져오기
            String originalFileName = Optional.ofNullable(file.getOriginalFilename())
                    .orElse("unknown");

            // S3에서 사용할 고유 Key 생성
            String fileKey = "uploads/" + UUID.randomUUID() + "-" + originalFileName;

            tempFile = prepareFile(file);

            return uploadToS3(fileKey,  tempFile, originalFileName, memberId, entityFileMapping);
        } catch (IOException e) {
            logger.error("File processing failed: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process the uploaded file", e);
        } finally {
            // 임시 파일 삭제
            if (tempFile != null && tempFile.exists() && !tempFile.delete()) {
                logger.warn("Failed to delete temporary file: {}", tempFile.getAbsolutePath());
            }
        }
    }

    private FileUploadResponseDTO uploadToS3(String key, File file, String originalFileName, String memberId, EntityFileMapping entityFileMapping) {
        try {

            s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                .build(),
                RequestBody.fromFile(file));

            logger.info("S3 업로드 성공: 파일 키 {}, 사용자 ID {}", key, memberId);
// S3 URL 생성
            String fileUrl = generateFileUrl(key);

            // Short URL Key 생성 (개선된 로직 적용)
            String shortUrlKey = generateShortUrlKey(key);

            FileMetadata metadata = new FileMetadata();
            metadata.setFileName(originalFileName);
            metadata.setFileKey(key);
            metadata.setShortUrlKey(shortUrlKey);
            metadata.setFileSize(file.length());
            metadata.setUploadByUserId(memberId);

            fileMetadataRepository.save(metadata);

            entityFileMapping.setFileMetadata(metadata);
            entityFileMappingRepository.save(entityFileMapping);

            return new FileUploadResponseDTO(key, fileUrl);

        } catch (S3Exception e) {
            logger.error("S3 업로드 실패: 파일 키 {}, 사용자 ID {}, 에러: {}", key, memberId, e.awsErrorDetails().errorMessage(), e);
            throw new RuntimeException("Failed to upload file to S3: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    public void deleteFileFromS3(String fileKey) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .build());
            logger.info("S3에서 파일 삭제 성공: {}", fileKey);
        } catch (S3Exception e) {
            logger.error("S3 파일 삭제 실패: {}", fileKey, e);
        }
    }


    public String getFullUrlByShortUrl(String shortUrlKey) {
        FileMetadata metadata = fileMetadataRepository.findByShortUrlKey(shortUrlKey)
                .orElseThrow(() -> new RuntimeException("Metadata not found"));
        String encodedFileName = URLEncoder.encode(metadata.getFileKey(), StandardCharsets.UTF_8);
        return String.format(s3BaseUrl + "%s", bucketName, encodedFileName);
    }

    public String getFullUrlByKey(String key) {
        String encodedFileName = URLEncoder.encode(key, StandardCharsets.UTF_8);
        return String.format(s3BaseUrl + "%s", bucketName, encodedFileName);
    }

    private String generateFileUrl(String fileKey) {
        String encodedFileName = URLEncoder.encode(fileKey, StandardCharsets.UTF_8);
        return String.format(s3BaseUrl + "%s", bucketName, encodedFileName);
    }

    private void validateFile(MultipartFile file) {
        if (file.getSize() > 10 * 1024 * 1024) { // 10MB 제한
            throw new FileSizeExceededException();
        }
    }

    private File prepareFile(MultipartFile file) throws IOException {
        validateFile(file);
        String originalFilename = Optional.ofNullable(file.getOriginalFilename())
                .orElse("unknown");
        String safeFileName = Paths.get(originalFilename).getFileName().toString();
        File tempFile = File.createTempFile("temp", safeFileName);
        file.transferTo(tempFile);
        return tempFile;
    }
}
