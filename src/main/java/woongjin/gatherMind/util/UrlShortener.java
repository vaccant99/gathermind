package woongjin.gatherMind.util;

import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

public class UrlShortener {

    public static String generateShortUrlKey(String fileKey) {
        // 파일 이름만 추출 (경로 제거)
        String fileName = Paths.get(fileKey).getFileName().toString();

        // 파일 이름 기반으로 UUID 생성 및 Base64 인코딩
        String uniqueKey = UUID.randomUUID() + "-" + fileName;
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(uniqueKey.getBytes())
                .substring(0, 8); // 8자로 제한
    }
}
