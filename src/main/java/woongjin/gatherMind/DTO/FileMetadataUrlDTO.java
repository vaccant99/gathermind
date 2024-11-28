package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FileMetadataUrlDTO {

    private Long fileMetadataId;

    private String fileName; // 업로드된 원본 파일 이름
    private String shortUrlKey; // 단축 URL 키
    private String fileKey; // S3에서 사용되는 고유 Key
    private Long fileSize; // 파일 크기
    private String uploadByUserId; // 업로더 ID
}
