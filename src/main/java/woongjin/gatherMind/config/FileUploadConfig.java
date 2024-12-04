package woongjin.gatherMind.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class FileUploadConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("10MB")); // 파일당 최대 크기
        factory.setMaxRequestSize(DataSize.parse("10MB")); // 요청 전체 최대 크기
        return factory.createMultipartConfig();
    }
}
