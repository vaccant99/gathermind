package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import woongjin.gatherMind.DTO.FileUploadResponseDTO;
import woongjin.gatherMind.exception.FileSizeExceededException;
import woongjin.gatherMind.service.FileService;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;


//    @Operation(
//            summary = "파일 업로드"
//    )
//    @PostMapping("/upload")
//    public ResponseEntity<FileUploadResponseDTO> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {
//
//        FileUploadResponseDTO fileUploadResponseDTO = fileService.handleFileUpload(file, userId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(fileUploadResponseDTO);
//    }

    @GetMapping("/default-profile")
    public ResponseEntity<Resource> getDefaultProfileImage() {
        try {
            Resource resource = (Resource) new ClassPathResource("static/images/default-profile.png");
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"default-profile.png\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
