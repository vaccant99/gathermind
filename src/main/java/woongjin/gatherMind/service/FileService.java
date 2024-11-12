package woongjin.gatherMind.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import woongjin.gatherMind.entity.FileMetadata;
import woongjin.gatherMind.repository.FileMetadataRepository;

import java.io.File;
import java.time.LocalDateTime;

@Service
public class FileService {

    private final S3Client s3Client;
    private final FileMetadataRepository fileMetadataRepository;

    @Value("${aws.s3.bucket:YOUR_BUCKET}")
    private String bucketName;

    public FileService (FileMetadataRepository fileMetadataRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
        this.s3Client = S3Client.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public String uploadFile(String key, File file, String userId) {
        try {
            s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                .build(),
                RequestBody.fromFile(file));
            String fileUrl =  "https://%s.s3.amazonaws.com/%s".formatted(bucketName, key);

            FileMetadata metadata = new FileMetadata();
            metadata.setFileName(file.getName());
            metadata.setUrl(fileUrl);
            metadata.setFileSize(file.length());
            metadata.setUploadByUserId(userId);

            fileMetadataRepository.save(metadata);

            return fileUrl;

        } catch (S3Exception e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }
}
