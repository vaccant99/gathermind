package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import woongjin.gatherMind.DTO.FileUploadResponseDTO;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.entity.EntityFileMapping;
import woongjin.gatherMind.service.FileService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;
    private final JwtTokenProvider jwtTokenProvider;

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

    @Operation(
            summary = "파일 업로드"
    )
    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(HttpServletRequest request,
                                                    @RequestParam("files[]") List<MultipartFile> files) {

//        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        String memberId = "member16";
        List<String> fileUrls = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                FileUploadResponseDTO responseDTO = fileService.handleFileUpload(file, memberId);
                fileUrls.add(responseDTO.getFileUrl());
            }
            return ResponseEntity.ok(fileUrls);
        } catch (Exception e) {
            logger.error("파일 업로드 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
