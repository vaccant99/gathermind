package woongjin.gatherMind.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.service.FileService;

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
//    }k()

}
