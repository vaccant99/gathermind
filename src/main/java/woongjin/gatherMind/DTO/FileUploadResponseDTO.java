package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponseDTO {

    private String fileKey; // 파일 이름 또는 키
    private String fileUrl; // S3 URL
}
